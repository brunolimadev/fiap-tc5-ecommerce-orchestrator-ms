package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.user.CreateUserRequestDto;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.user.CreateUserResponseDto;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_user.GetUserByEmailResponse;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;

    @Value("${microservices.user-ms.url}")
    private String userMsUrl;

    public UserServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Método responsável por criar um usuário
     *
     * @param userDto
     */
    @Override
    public CreateUserResponseDto create(CreateUserRequestDto userDto) {

        try {

            this.restTemplate.postForEntity(userMsUrl, userDto, null);

            return new CreateUserResponseDto(userDto.name());
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar o usuário! Por favor, tente novamente.");
        }

    }

    @Override
    public Optional<UserDetails> findByEmail(String email) {

        try {
            String url = new StringBuilder(userMsUrl)
                    .append("?byEmail=")
                    .append(email)
                    .toString();

            ResponseEntity<GetUserByEmailResponse> response = this.restTemplate.getForEntity(url,
                    GetUserByEmailResponse.class);

            return Optional.of(response.getBody());
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao validar usuário!");
        }
    }
}
