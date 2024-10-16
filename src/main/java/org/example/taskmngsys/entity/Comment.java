package org.example.taskmngsys.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "task_management", name = "t_comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text", unique = false, nullable = false)
    private String text;

//    @ManyToOne
//    @JoinColumn(name = "id", nullable = false)
//    private Task task;
}
