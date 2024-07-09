package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSessionRequestDto {
    private String username;
}
