package org.example.taskmngsys.exception;

public class UserExistException extends RuntimeException {
    public UserExistException(String username) {
        super( username + " уже существует");
    }
}