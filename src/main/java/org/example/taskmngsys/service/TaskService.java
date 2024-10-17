package org.example.taskmngsys.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmngsys.dto.user.task.NewTaskRequest;
import org.example.taskmngsys.dto.user.task.UpdateTaskRequest;
import org.example.taskmngsys.dto.user.task.UpdateTaskResponse;
import org.example.taskmngsys.emum.Status;
import org.example.taskmngsys.entity.Task;
import org.example.taskmngsys.entity.User;
import org.example.taskmngsys.exception.UserNotFoundException;
import org.example.taskmngsys.repository.TaskRepository;
import org.example.taskmngsys.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    public List<Task> findAllByUserId(Integer userId) {
        return taskRepository.findAllByAuthorId(userId);
    }

    public Task save(NewTaskRequest request,
                     User user,
                     User executor) {

        Task task = new Task(null,
                request.getHeader(),
                request.getDescription(),
                request.getStatus(),
                request.getPriority(),
                user,
                executor);
        return taskRepository.save(task);
    }

    public Optional<Task> findByUserIdAndTaskId(Integer userId, Integer taskId) {
        return taskRepository.findByUserIdAndTaskId(userId, taskId);
    }

    public void delete(Integer taskId) {
        taskRepository.deleteById(taskId);
    }

    public UpdateTaskResponse updateTask(Task oldTask, UpdateTaskRequest updateRequest) {
        Task updatedTask = oldTask;

        if (!updateRequest.getHeader().isEmpty()) {
            updatedTask.setHeader(updateRequest.getHeader());
        }
        if (!updateRequest.getDescription().isEmpty()) {
            updatedTask.setDescription(updateRequest.getDescription());
        }
        if (updateRequest.getStatus() != null) {
            updatedTask.setStatus(updateRequest.getStatus());
        }
        if (updateRequest.getPriority() != null) {
            updatedTask.setPriority(updateRequest.getPriority());
        }
        if (!updateRequest.getExecutorName().isEmpty()) {
            var newExecutor = userRepository.findByUsername(updateRequest.getExecutorName());
            newExecutor.ifPresent(updatedTask::setExecutor);
        }
        taskRepository.save(updatedTask);

        return new UpdateTaskResponse(
                oldTask.getId(),
                oldTask.getHeader(),
                oldTask.getDescription(),
                oldTask.getStatus(),
                oldTask.getPriority(),
                oldTask.getExecutor().getUsername()
        );

    }

    public void updateTaskStatus(Task task, Status status) {
        task.setStatus(status);
        taskRepository.save(task);
    }

    public void updateTaskExecutor(Task task, String executorName) {
        Optional<User> executorOptional = userRepository.findByUsername(executorName);
        User executor = executorOptional.orElseThrow(() -> new UserNotFoundException("исполнитель " + executorName));
        task.setExecutor(executor);
        taskRepository.save(task);
    }
}
