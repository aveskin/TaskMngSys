package org.example.taskmngsys.dto.user.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.taskmngsys.emum.Priority;
import org.example.taskmngsys.emum.Status;

@Data
public class UpdateTaskRequest {
    @Schema(description = "Id задачи", example = "1")
    @NotNull(message = "Id задачи не может быть пустым")
    private Integer id;

    @Schema(description = "Заголовок", example = "Some header")
    private String header;

    @Schema(description = "Описание", example = "Some description")
    private String description;

    @Schema(description = "Статус", example = "возможные статусы: AWAITING, IN_PROGRESS, FINISHED")
    private Status status;

    @Schema(description = "Приоритет", example = "возможные приоритеты: LOW, MIDDLE, HIGH")
    private Priority priority;

    @Schema(description = "Имя исполнителя", example = "SomeUser")
    private String executorName;
}
