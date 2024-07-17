package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.impl;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.session.CreateSessionRequestDto;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.session.CreateSessionResponseDto;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.session.GetRevokedTokenResponseDto;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_session.CreateSessionRequest;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_session.CreateSessionResponse;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_session.GetRevokedTokenResponse;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_session.GetSessionResponse;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.SessionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
    public CreateSessionResponseDto createSession(CreateSessionRequestDto request) {

        CreateSessionRequest createSessionRequest = new CreateSessionRequest(request.getUsername(), request.getToken());

        try {

            String url = new StringBuilder(
                    sessionMsUrl)
                    .toString();

            ResponseEntity<CreateSessionResponse> response = this.restTemplate.postForEntity(url,
                    createSessionRequest,
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

    @SuppressWarnings("null")
    @Override
    public GetRevokedTokenResponseDto getRevokedToken(String sessionId) {
        try {
            String url = new StringBuilder(
                    sessionMsUrl)
                    .append("/revoked-tokens")
                    .append("/")
                    .append(sessionId)
                    .toString();

            var response = restTemplate.getForEntity(url, GetRevokedTokenResponse.class);

            GetRevokedTokenResponseDto dto = new GetRevokedTokenResponseDto();

            BeanUtils.copyProperties(response.getBody(), dto);

            return dto;

        } catch (HttpClientErrorException e) {
            e.printStackTrace();

            if (e.getStatusCode().value() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                return null;
            }

            throw new RuntimeException("Erro ao obter token revogado!");
        }
    }

    @Override
    public GetSessionResponse getSession(String sessionId) {

        try {

            String url = new StringBuilder(sessionMsUrl)
                    .append("/sessionId")
                    .append("/")
                    .append(sessionId)
                    .toString();

            var response = restTemplate.getForEntity(url, GetRevokedTokenResponse.class);
            var responseDto = new GetSessionResponse();
            BeanUtils.copyProperties(response, responseDto);
            return responseDto;

        } catch (HttpClientErrorException e) {
            e.printStackTrace();

            if (e.getStatusCode().value() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                return null;
            }
            throw new RuntimeException("Erro ao obter os dados da sessão!");
        }

    }

}
