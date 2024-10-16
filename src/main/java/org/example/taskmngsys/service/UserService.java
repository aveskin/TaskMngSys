package org.example.taskmngsys.service;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.example.taskmngsys.entity.User;
import org.example.taskmngsys.exception.EmailExistException;
import org.example.taskmngsys.exception.EmailNotExistException;
import org.example.taskmngsys.exception.UserExistException;
import org.example.taskmngsys.exception.UserNotFoundException;
import org.example.taskmngsys.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;


    public User save(User user) {
        return repository.save(user);
    }


    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new UserExistException(user.getUsername());
        }
        if (repository.existsByEmail(user.getEmail())) {
            throw new EmailExistException(user.getEmail());
        }

        return save(user);
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    public User getByEmail(@Email(message = "Email адрес должен быть в формате user@example.com") String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new EmailNotExistException(email));
    }
}