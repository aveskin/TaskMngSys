package org.example.taskmngsys.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmngsys.dto.user.comment.NewCommentRequest;
import org.example.taskmngsys.entity.Comment;
import org.example.taskmngsys.entity.Task;
import org.example.taskmngsys.entity.User;
import org.example.taskmngsys.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment save(NewCommentRequest request, User user, Task task) {
        Comment comment = new Comment(null, request.getText(), user.getUsername(), task);
        return commentRepository.save(comment);
    }
}
