package org.example.taskmngsys.dto.user.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTaskExecutorRequest {
    @Schema(description = "Id задачи", example = "1")
    @NotNull(message = "Id задачи не может быть пустым")
    private Integer id;

    @Schema(description = "имя исполнителя", example = "SomeUser")
    private String executorName;
}
