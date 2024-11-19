package org.example.authmicroservice.exception;

public class UserExistException extends RuntimeException {
    public UserExistException(String username) {
        super( username + " уже существует");
    }
}