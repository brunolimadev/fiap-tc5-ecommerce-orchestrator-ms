package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.session;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetRevokedTokenResponseDto {
    private String sessionId;
    private String username;
    private String token;
    private LocalDateTime createdAt;
}
