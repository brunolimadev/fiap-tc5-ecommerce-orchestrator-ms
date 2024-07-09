package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.session.CreateSessionResponseDto;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_session.CreateSessionRequest;

public interface SessionService {

    public CreateSessionResponseDto createSession(CreateSessionRequest request);

    public void deleteSession(String sessionId);

}
