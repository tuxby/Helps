package by.tux.helps.models;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "helps_requests")
public class RequestModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "status")
    private String status;

}
