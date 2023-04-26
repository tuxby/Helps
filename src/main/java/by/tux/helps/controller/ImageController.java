package by.tux.helps.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import by.tux.helps.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import by.tux.helps.models.ImageModel;
import by.tux.helps.models.ImageResponseModel;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image,@RequestParam("message_id") Long messageId) {
        try {
            imageService.addImage(image, messageId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File uploaded successfully: %s", image.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not upload the file: %s!", image.getOriginalFilename()));
        }
    }

    @GetMapping
    public List<ImageResponseModel> list() {
        return imageService.getAllFiles()
                .stream()
                .map(this::mapToFileResponse)
                .collect(Collectors.toList());
    }

    private ImageResponseModel mapToFileResponse(ImageModel imageModel) {
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/")
                .path(imageModel.getId().toString())
                .toUriString();
        ImageResponseModel photoResponse = new ImageResponseModel();
        photoResponse.setId(imageModel.getId());
        photoResponse.setName(imageModel.getName());
        photoResponse.setContentType(imageModel.getContentType());
        photoResponse.setSize(imageModel.getSize());
        photoResponse.setUrl(downloadURL);
        photoResponse.setAuthorId(imageModel.getAuthorId());

        return photoResponse;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) {
        Optional<ImageModel> photoModelOptional = imageService.getImage(id);

        if (!photoModelOptional.isPresent()) {
            return ResponseEntity.notFound()
                    .build();
        }ImageModel imageModel = photoModelOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageModel.getName() + "\"")
                .contentType(MediaType.valueOf(imageModel.getContentType()))
                .body(imageModel.getData());
    }

    @GetMapping("/del/{id}")
    public ResponseEntity<String>delete(@PathVariable Long id){
        imageService.delImage(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/edit/{id}")
    public boolean editImage(@PathVariable Long id,@RequestParam("image") MultipartFile image,@RequestParam("message_id") Long messageId) {
        try{
            return imageService.editImage(id , image, messageId);
        }
        catch (Exception ex){
            return false;
        }
    }
}