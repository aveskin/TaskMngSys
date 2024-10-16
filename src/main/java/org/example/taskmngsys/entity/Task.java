package org.example.taskmngsys.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.taskmngsys.emum.Priority;
import org.example.taskmngsys.emum.Status;

import java.util.List;


@Data
@Entity
@Table(schema = "task_management",name = "t_tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "header", nullable = false)
    private String header;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_id", nullable = false)
    private Status status;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "priority_id", nullable = false)
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "executor_id", nullable = false)
    private User executor;

//    @OneToMany
//    @JoinColumn(name = "comment_id", nullable = false)
//    private List<Comment> comments;
}
