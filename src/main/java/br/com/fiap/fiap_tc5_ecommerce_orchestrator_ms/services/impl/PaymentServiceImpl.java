package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.impl;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.payment.PaymentRequestDTO;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.payment.PaymentRequestIntegrationDTO;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.session.GetSessionResponseDto;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.PaymentService;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.SessionService;
import com.google.gson.Gson;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final SessionService sessionService;

    private final RestTemplate restTemplate;

    private static final String SESSION_ID_HEADER = "session-id";

    @Value("${microservices.payment-ms.url}")
    private String paymentMsUrl;

    public PaymentServiceImpl(SessionService sessionService, RestTemplate restTemplate) {
        this.sessionService = sessionService;
        this.restTemplate = restTemplate;
    }

    @Override
    public Object processPayment(PaymentRequestDTO paymentRequestDTO, String sessionId) {

        try {

            // TODO Validar se o ID do carrinho é igual ao da Sessão (Segurança)

            // Resgata a sessão
            GetSessionResponseDto sessionResponse = sessionService.getSession(sessionId);

            // Resgata os dados da sessão
            final Object sessionData = sessionResponse.getSessionData();

            // Resgata o valor total dos itens do carrinho
            Double shoppingCardAmount = getShoppingCartTotalOrder(sessionData);

            // Monta solicitação com o valor total do carrinho
            var paymentRequestIntegrationDTO = new PaymentRequestIntegrationDTO(
                    paymentRequestDTO.idShoppingCart(),
                    paymentRequestDTO.cardRequestDTO(),
                    shoppingCardAmount);

            // Monta o Header da requisição
            HttpHeaders headers = new HttpHeaders();
            headers.set(SESSION_ID_HEADER, sessionId);

            HttpEntity<PaymentRequestIntegrationDTO> requestEntity;
            requestEntity = new HttpEntity<>(paymentRequestIntegrationDTO, headers);

            // Envia a requisição HTTP POST para o microserviço de pagamento
            ResponseEntity<?> response = restTemplate.exchange(paymentMsUrl, HttpMethod.POST,
                    requestEntity, Object.class);

            return ((List<Object>) response.getBody()).get(0);

        } catch (RestClientException exception) {

            throw new RuntimeException(
                    "Ocorreu um erro durante o processamento do pagamento! :: " + exception.getMessage());
        }

    }

    private Double getShoppingCartTotalOrder(Object sessionData) {

        try {
            var sessionJsonString = new Gson().toJson(sessionData);
            return new JSONObject(sessionJsonString)
                    .getJSONObject("shopping_cart")
                    .getDouble("totalOrder");

        } catch (JSONException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    private String getIdShoppingCart(Object sessionData) {

        try {
            var sessionJsonString = new Gson().toJson(sessionData);

            return new JSONObject(sessionJsonString)
                    .getJSONObject("sessionData")
                    .getJSONObject("shopping_cart")
                    .getString("id");

        } catch (JSONException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}