package by.tux.crm.models;

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
    private long authorId;

    @Column(name = "manager_id")
    private long managerId;

    @Column(name = "status")
    private String status;

}
