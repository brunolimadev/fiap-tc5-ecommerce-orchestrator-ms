package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.session.CreateSessionResponseDto;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_session.CreateSessionRequest;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_session.CreateSessionResponse;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.SessionService;

@Service
public class SessionServiceImpl implements SessionService {

    private final RestTemplate restTemplate;

    @Value("${microservices.session-ms.url}")
    private String sessionMsUrl;

    public SessionServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("null")
    @Override
    public CreateSessionResponseDto createSession(CreateSessionRequest request) {

        CreateSessionRequest sessionRequest = new CreateSessionRequest(request.getUsername());

        try {
            String url = new StringBuilder(
                    sessionMsUrl)
                    .toString();

            ResponseEntity<CreateSessionResponse> response = this.restTemplate.postForEntity(url,
                    sessionRequest,
                    CreateSessionResponse.class);

            CreateSessionResponseDto responseDto = new CreateSessionResponseDto();

            BeanUtils.copyProperties(response.getBody(), responseDto);

            return responseDto;
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar sessão!");
        }
    }

    @Override
    public void deleteSession(String sessionId) {
        try {
            String url = new StringBuilder(
                    sessionMsUrl)
                    .append("/")
                    .append(sessionId)
                    .toString();

            restTemplate.delete(url);
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar sessão!");
        }
    }

}
