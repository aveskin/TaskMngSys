package org.example.taskmngsys.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Tag(name = "Операции пользователя")
@RequestMapping("/api/secured")
public class UserController {


    @Operation(summary = "Получение текущего пользователя")
    @GetMapping("/user")
    public String accessUser(Principal principal){
        if(principal==null){
            return null;
        }
        return principal.getName();
    }
}
