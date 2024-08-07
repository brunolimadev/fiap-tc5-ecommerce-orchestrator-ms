package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_session;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetSessionResponse {

    private String sessionId;

    private String userName;

    private LocalDateTime createAt;

    private LocalDateTime expiresAt;

    private Object sessionData;
}
