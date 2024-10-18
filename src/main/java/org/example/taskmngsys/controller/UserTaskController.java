package org.example.taskmngsys.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskmngsys.dto.user.comment.NewCommentResponse;
import org.example.taskmngsys.dto.user.task.GetTaskRequest;
import org.example.taskmngsys.dto.user.task.GetTaskResponse;
import org.example.taskmngsys.dto.user.task.UpdateTaskStatusIfExecutorRequest;
import org.example.taskmngsys.entity.Task;
import org.example.taskmngsys.entity.User;
import org.example.taskmngsys.exception.TaskNotFoundException;
import org.example.taskmngsys.service.TaskService;
import org.example.taskmngsys.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Операции пользователя с любыми(чужими) задачами")
@RequestMapping("/api/secured/user")
public class UserTaskController {
    private final UserService userService;
    private final TaskService taskService;


    @Operation(summary = "Получение задачи любого пользователя по Id задачи")
    @GetMapping("/task")
    public ResponseEntity<GetTaskResponse> getTask(@RequestBody @Valid GetTaskRequest request) {
        var taskOptional = taskService.findByTaskId(request.getId());
        Task task = taskOptional.orElseThrow(() -> new TaskNotFoundException(request.getId()));

        List<NewCommentResponse> commentsList =
                task.getComments()
                        .stream()
                        .map(comment -> new NewCommentResponse(comment.getId(), comment.getText(), comment.getAuthorName()))
                        .toList();

        GetTaskResponse getTaskResponse = new GetTaskResponse(
                task.getId(),
                task.getHeader(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getExecutor().getUsername(),
                commentsList
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getTaskResponse);

    }

    @Operation(summary = "Изменение статуса задачи у пользователя по Id задачи и имени пользователя," +
            " если он является исполнителем")
    @PatchMapping("/change-status-if-executor")
    public ResponseEntity<Void> changeStatusIfExecutor(@RequestBody @Valid UpdateTaskStatusIfExecutorRequest request) {
        User user = userService.getByUsername(request.getUserName());
        var taskOptional = taskService.findByUserIdAndTaskIdWhereUserExecutor(user.getId(), request.getTaskId());
        Task task = taskOptional.orElseThrow(() -> new TaskNotFoundException(request.getTaskId()));
        taskService.updateTaskStatus(task, request.getStatus());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }


}
