package by.tux.helps.models;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "helps_images")
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String contentType;

    private Long size;

    @Lob
    private byte[] data;

    @Column(name = "message_id")
    private long authorId;

}