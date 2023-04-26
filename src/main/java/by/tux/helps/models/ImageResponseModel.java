package by.tux.helps.models;

import lombok.Data;

@Data
public class ImageResponseModel {
    private Long id;
    private String name;
    private Long size;
    private String url;
    private String contentType;

    private long authorId;
}
