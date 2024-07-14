package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.session.CreateSessionRequestDto;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.session.CreateSessionResponseDto;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.session.GetRevokedTokenResponseDto;

public interface SessionService {

    public CreateSessionResponseDto createSession(CreateSessionRequestDto request);

    public void deleteSession(String sessionId);

    public GetRevokedTokenResponseDto getRevokedToken(String sessionId);

}
