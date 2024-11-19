package org.example.authmicroservice.exception;

public class EmailExistException extends RuntimeException {
    public EmailExistException(String email) {
        super( email + " email уже существует");
    }
}