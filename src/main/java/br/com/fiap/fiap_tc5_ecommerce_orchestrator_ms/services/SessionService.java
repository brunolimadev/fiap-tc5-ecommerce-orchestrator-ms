package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services;

public interface SessionService {

    public void createSession(Object object);

    public Object getSession(String sessionId);

}
