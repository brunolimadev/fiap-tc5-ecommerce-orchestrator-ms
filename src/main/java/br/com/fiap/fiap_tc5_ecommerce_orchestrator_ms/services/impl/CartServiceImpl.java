package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.impl;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_cart.CartItemModel;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_cart.CartModel;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.CartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class CartServiceImpl implements CartService {

  private final RestTemplate restTemplate;

  private static final String SESSION_ID_HEADER = "session-id";

  @Value("${microservices.cart-ms.url}")
  private String cartMsUrl;

  public CartServiceImpl(RestTemplate restTemplate) {

    this.restTemplate = restTemplate;

  }

  @Override
  public CartModel addCartItem(CartItemModel cartItemModel, String sessionId) {

    try {

      HttpHeaders headers = new HttpHeaders();
      headers.set(SESSION_ID_HEADER, sessionId);

      HttpEntity<?> requestEntity = new HttpEntity<>(cartItemModel, headers);

      ResponseEntity<CartModel> response = restTemplate
              .exchange(
                      cartMsUrl, HttpMethod.POST, requestEntity, CartModel.class
              );

      return response.getBody();

    } catch (RestClientException e) {

      e.printStackTrace();
      throw new RuntimeException("Erro ao criar o item! Por favor, tente novamente.");

    }

  }

  @Override
  public CartModel removeCartItem(Long id, String sessionId) {

    try {

      HttpHeaders headers = new HttpHeaders();
      headers.set(SESSION_ID_HEADER, sessionId);

      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

      String url = cartMsUrl + "/" + id;

      ResponseEntity<CartModel> response = restTemplate.exchange(
              url, HttpMethod.DELETE, requestEntity, CartModel.class);

      return response.getBody();

    } catch (RestClientException e) {

      e.printStackTrace();
      throw new RuntimeException("Erro ao validar usuário!");

    }

  }

}