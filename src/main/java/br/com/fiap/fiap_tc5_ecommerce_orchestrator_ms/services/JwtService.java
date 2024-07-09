package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_user.GetUserByEmailResponse;

public interface JwtService {
    public String generateToken(GetUserByEmailResponse user, String sessionId) throws Exception;

    public String validateToken(String token);
}
