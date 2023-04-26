package by.tux.helps.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import by.tux.helps.models.ImageModel;
import by.tux.helps.repository.ImageRepository;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void addImage(MultipartFile image, Long autorId) throws IOException {
        ImageModel photoModel = new ImageModel();
        photoModel.setName(StringUtils.cleanPath(image.getOriginalFilename()));
        photoModel.setContentType(image.getContentType());
        photoModel.setData(image.getBytes());
        photoModel.setSize(image.getSize());

        photoModel.setAuthorId(autorId);

        imageRepository.save(photoModel);
    }

    public Optional<ImageModel> getImage(Long id) {
        return imageRepository.findById(id);
    }

    public List<ImageModel> getAllFiles() {
        return imageRepository.findAll();
    }

    public List<ImageModel> getByAuthorId(Long authorId) {
        return imageRepository.findByAuthorId(authorId);
    }

    public void delImage(Long id) {
        imageRepository.deleteById(id);
    }

    public boolean editImage(Long id, MultipartFile photo, Long message_id) throws IOException {
        Optional<ImageModel> photoModelOptional = imageRepository.findById(id);
        if (!photoModelOptional.isPresent()) {
            return false;
        }
        ImageModel photoModel = photoModelOptional.get();
        photoModel.setName(StringUtils.cleanPath(photo.getOriginalFilename()));
        photoModel.setContentType(photo.getContentType());
        photoModel.setData(photo.getBytes());
        photoModel.setSize(photo.getSize());
        photoModel.setAuthorId(message_id);

        imageRepository.save(photoModel);
        return true;
    }
}