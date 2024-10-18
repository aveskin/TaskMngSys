package org.example.taskmngsys.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskmngsys.dto.user.comment.NewCommentRequest;
import org.example.taskmngsys.dto.user.comment.NewCommentResponse;
import org.example.taskmngsys.entity.Comment;
import org.example.taskmngsys.entity.Task;
import org.example.taskmngsys.entity.User;
import org.example.taskmngsys.exception.TaskNotFoundException;
import org.example.taskmngsys.service.CommentService;
import org.example.taskmngsys.service.TaskService;
import org.example.taskmngsys.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Операции пользователя с комментариями")
@RequestMapping("/api/secured/user")
public class UserCommentController {
    private final UserService userService;
    private final TaskService taskService;
    private final CommentService commentService;


    @Operation(summary = "Создание комментария к задаче")
    @PostMapping("/create-comment")
    public ResponseEntity<NewCommentResponse> createNewTask(@RequestBody @Valid NewCommentRequest request) {
        User user = userService.getCurrentUser();
        Optional<Task> taskOptional = taskService.findByTaskId(request.getTaskId());
        Task task = taskOptional.orElseThrow(() -> new TaskNotFoundException(request.getTaskId()));
        Comment comment = commentService.save(request, user, task);

        NewCommentResponse newCommentResponse = new NewCommentResponse(
                comment.getId(),
                comment.getText(),
                comment.getAuthorName()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newCommentResponse);
    }


}
