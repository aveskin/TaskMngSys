package org.example.authmicroservice.service;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.example.authmicroservice.entity.User;
import org.example.authmicroservice.exception.EmailExistException;
import org.example.authmicroservice.exception.EmailNotExistException;
import org.example.authmicroservice.exception.UserExistException;
import org.example.authmicroservice.exception.UserNotFoundException;
import org.example.authmicroservice.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserExistException(user.getUsername());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailExistException(user.getEmail());
        }

        return save(user);
    }

    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

    }

    @Transactional(readOnly = true)
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Transactional(readOnly = true)
    public User getByEmail(@Email(message = "Email адрес должен быть в формате user@example.com") String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotExistException(email));
    }
}