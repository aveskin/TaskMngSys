package org.example.taskmngsys.repository;

import org.example.taskmngsys.entity.Comment;
import org.example.taskmngsys.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
