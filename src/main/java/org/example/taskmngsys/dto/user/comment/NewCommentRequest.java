package org.example.taskmngsys.dto.user.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.taskmngsys.emum.Priority;
import org.example.taskmngsys.emum.Status;

@Data
public class NewCommentRequest {
    @Schema(description = "Id задачи", example = "2")
    @NotNull(message = "Id задачи не может быть пустым")
    private Integer taskId;

    @Schema(description = "Текст комментария", example = "Some text")
    @NotBlank(message = "Текст не может быть пустым")
    private String text;
}
