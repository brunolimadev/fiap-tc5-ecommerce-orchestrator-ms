package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.user.CreateUserRequestDto;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequestDto request) {
        userService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
