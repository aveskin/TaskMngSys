package org.example.taskmngsys.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmngsys.dto.security.JwtAuthenticationResponse;
import org.example.taskmngsys.dto.security.SignInRequest;
import org.example.taskmngsys.dto.security.SignUpRequest;
import org.example.taskmngsys.entity.User;
import org.example.taskmngsys.exception.UserNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }


    public JwtAuthenticationResponse signIn(SignInRequest request) {
        String username;
        username = request.getUsername();
        if (username == null) {
            User user = userService.getByEmail(request.getEmail());
            username = user.getUsername();
        }

        if (username == null) {
            throw new UserNotFoundException("пользователь");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username,
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(username);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}