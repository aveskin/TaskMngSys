package org.example.taskmngsys.dto.user.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.taskmngsys.emum.Priority;
import org.example.taskmngsys.emum.Status;

@Data
public class NewTaskRequest {

    @Schema(description = "Заголовок", example = "Some header")
    @NotBlank(message = "Заголовок не может быть пустым")
    private String header;

    @Schema(description = "Описание", example = "Some description")
    @NotBlank(message = "Описание не может быть пустым")
    private String description;

    @Schema(description = "Статус", example = "возможные статусы: AWAITING, IN_PROGRESS, FINISHED")
    @NotNull(message = "Статус не может быть пустым")
    private Status status;

    @Schema(description = "Приоритет", example = "возможные приоритеты: LOW, MIDDLE, HIGH")
    @NotNull(message = "Приоритет не может быть пустым")
    private Priority priority;

    @Schema(description = "Имя исполнителя", example = "SomeUser")
    @NotBlank(message = "Имя исполнителя не может быть пустым")
    private String executorName;
}
