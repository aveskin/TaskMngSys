package org.example.taskmngsys.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Integer taskId) {
        super("задача id=" + taskId + " не найденa, или не принадлежит текущему пользователю");
    }
}