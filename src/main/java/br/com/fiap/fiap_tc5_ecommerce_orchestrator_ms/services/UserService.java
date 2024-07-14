package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.user.CreateUserRequestDto;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.user.CreateUserResponseDto;

public interface UserService {

    public CreateUserResponseDto create(CreateUserRequestDto user);

    public Optional<UserDetails> findByEmail(String email);

}
