package by.tux.helps.models;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "helps_responses")
public class ResponseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "request_id")
    private long requestId;

    @Column(name = "status")
    private String status;

}
