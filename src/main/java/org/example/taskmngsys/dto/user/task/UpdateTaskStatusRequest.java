package org.example.taskmngsys.dto.user.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.taskmngsys.emum.Status;

@Data
public class UpdateTaskStatusRequest {
    @Schema(description = "Id задачи", example = "1")
    @NotNull(message = "Id задачи не может быть пустым")
    private Integer id;

    @Schema(description = "Статус", example = "возможные статусы: AWAITING, IN_PROGRESS, FINISHED")
    private Status status;
}
