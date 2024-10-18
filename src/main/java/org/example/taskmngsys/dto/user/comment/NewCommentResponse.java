package org.example.taskmngsys.dto.user.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewCommentResponse {
    private Integer id;
    private String text;
    private String authorName;
}
