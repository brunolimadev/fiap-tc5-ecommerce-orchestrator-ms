package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_users;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private UUID id;

    private String name;

    private String email;

    private String password;

    private boolean isActive;

    private LocalDateTime createdAt;

    private UserRoleEnum role;
}
