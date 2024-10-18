package org.example.taskmngsys.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskmngsys.dto.user.comment.NewCommentResponse;
import org.example.taskmngsys.dto.user.task.*;
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
@Tag(name = "Операции пользователя со своими задачами")
@RequestMapping("/api/secured/user")
public class UserSelfTaskController {
    private final UserService userService;
    private final TaskService taskService;

    @Operation(summary = "Получение списка задач текущего пользователя")
    @GetMapping("/self-task-list")
    public List<GetTaskResponse> getSelfTaskList() {
        User user = userService.getCurrentUser();
        List<Task> tasks = taskService.findAllByUserId(user.getId());

        List<NewCommentResponse> commentsList =
                tasks.stream()
                        .flatMap(task -> task.getComments().stream()
                                .map(comment -> new NewCommentResponse(comment.getId(), comment.getText(), comment.getAuthorName()))
                        )
                        .toList();

        return tasks.stream()
                .map(task -> new GetTaskResponse(
                        task.getId(),
                        task.getHeader(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getPriority(),
                        task.getExecutor().getUsername(),
                        commentsList
                ))
                .toList();
    }

    @Operation(summary = "Получение задачи текущего пользователя по Id")
    @GetMapping("/self-task")
    public ResponseEntity<GetTaskResponse> getSelfTask(@RequestBody @Valid GetTaskRequest request) {
        User user = userService.getCurrentUser();
        var taskOptional = taskService.findByUserIdAndTaskId(user.getId(), request.getId());
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

    @Operation(summary = "Создание задачи у текущего пользователя")
    @PostMapping("/create-task")
    public ResponseEntity<NewTaskResponse> createNewTask(@RequestBody @Valid NewTaskRequest request) {
        User user = userService.getCurrentUser();
        User executor = userService.getByUsername(request.getExecutorName());
        Task task = taskService.save(request, user, executor);

        NewTaskResponse newTaskResponse = new NewTaskResponse(
                task.getId(),
                task.getHeader(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getExecutor().getUsername());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newTaskResponse);
    }

    @Operation(summary = "Удаление задачи у текущего пользователя")
    @DeleteMapping("/delete-task")
    public ResponseEntity<Void> deleteTask(@RequestBody @Valid DeleteTaskRequest request) {
        User user = userService.getCurrentUser();
        var taskOptional = taskService.findByUserIdAndTaskId(user.getId(), request.getId());
        if (taskOptional.isEmpty()) {
            throw new TaskNotFoundException(request.getId());
        }
        taskService.delete(request.getId());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }

    @Operation(summary = "Изменение задачи у текущего пользователя")
    @PutMapping("/update-task")
    public ResponseEntity<UpdateTaskResponse> updateTask(@RequestBody @Valid UpdateTaskRequest request) {
        User user = userService.getCurrentUser();
        var taskOptional = taskService.findByUserIdAndTaskId(user.getId(), request.getId());
        Task task = taskOptional.orElseThrow(() -> new TaskNotFoundException(request.getId()));
        UpdateTaskResponse updatedTask = taskService.updateTask(task, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedTask);
    }

    @Operation(summary = "Изменение статуса задачи у текущего пользователя")
    @PatchMapping("/change-status")
    public ResponseEntity<Void> changeStatus(@RequestBody @Valid UpdateTaskStatusRequest request) {
        User user = userService.getCurrentUser();
        var taskOptional = taskService.findByUserIdAndTaskId(user.getId(), request.getId());
        Task task = taskOptional.orElseThrow(() -> new TaskNotFoundException(request.getId()));
        taskService.updateTaskStatus(task, request.getStatus());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }

    @Operation(summary = "Назначение исполнителя задачи у текущего пользователя")
    @PatchMapping("/set-executor")
    public ResponseEntity<Void> changeExecutor(@RequestBody @Valid UpdateTaskExecutorRequest request) {
        User user = userService.getCurrentUser();
        var taskOptional = taskService.findByUserIdAndTaskId(user.getId(), request.getId());
        if (taskOptional.isEmpty()) {
            throw new TaskNotFoundException(request.getId());
        }
        Task task = taskOptional.get();
        taskService.updateTaskExecutor(task, request.getExecutorName());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }


}
