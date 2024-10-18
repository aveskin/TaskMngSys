package org.example.taskmngsys.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "task_management", name = "t_comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text", unique = false, nullable = false)
    private String text;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
