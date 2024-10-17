package org.example.taskmngsys.dto.user.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.taskmngsys.emum.Priority;
import org.example.taskmngsys.emum.Status;

@Data
@AllArgsConstructor
public class GetTaskResponse {
    private Integer id;
    private String header;
    private String description;
    private Status status;
    private Priority priority;
    private String executorName;
}
