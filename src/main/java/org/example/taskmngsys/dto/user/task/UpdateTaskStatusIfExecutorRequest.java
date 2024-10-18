package org.example.taskmngsys.dto.user.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.taskmngsys.emum.Status;

@Data
public class UpdateTaskStatusIfExecutorRequest {
    @Schema(description = "Id задачи", example = "1")
    @NotNull(message = "Id задачи не может быть пустым")
    private Integer taskId;

    @Schema(description = "имя пользователя", example = "SomeUser")
    @NotBlank(message = "имя пользователя не может быть пустым")
    private String userName;

    @Schema(description = "Статус", example = "возможные статусы: AWAITING, IN_PROGRESS, FINISHED")
    private Status status;
}
