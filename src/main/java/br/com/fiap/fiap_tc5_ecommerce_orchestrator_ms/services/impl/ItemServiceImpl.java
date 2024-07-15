package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.impl;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_item.ItemModel;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.ItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ItemServiceImpl implements ItemService {

  private final RestTemplate restTemplate;

  private static final String SESSION_ID_HEADER = "session-id";

  @Value("${microservices.item-ms.url}")
  private String itemMsUrl;

  public ItemServiceImpl(RestTemplate restTemplate) {

    this.restTemplate = restTemplate;

  }

  @Override
  public ItemModel createItem(ItemModel itemModel, String sessionId) {

    try {

      HttpHeaders headers = new HttpHeaders();
      headers.set(SESSION_ID_HEADER, sessionId);

      HttpEntity<ItemModel> requestEntity = new HttpEntity<>(itemModel, headers);

      ResponseEntity<ItemModel> response = restTemplate
              .exchange(
                      itemMsUrl, HttpMethod.POST, requestEntity, ItemModel.class
              );

      return response.getBody();

    } catch (RestClientException e) {

      e.printStackTrace();
      throw new RuntimeException("Erro ao criar o item! Por favor, tente novamente.");

    }

  }

  @Override
  public List<ItemModel> getItems(String sessionId) {

    try {

      HttpHeaders headers = new HttpHeaders();
      headers.set(SESSION_ID_HEADER, sessionId);

      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

      ResponseEntity<ItemModel[]> response = restTemplate.exchange(
              itemMsUrl, HttpMethod.GET, requestEntity, ItemModel[].class);

      return Arrays.stream(Objects.requireNonNull(response.getBody())).toList();

    } catch (RestClientException e) {

      e.printStackTrace();
      throw new RuntimeException("Erro ao validar usuário!");

    }

  }

  @Override
  public ItemModel getItem(Long id, String sessionId) {

    try {

      HttpHeaders headers = new HttpHeaders();
      headers.set(SESSION_ID_HEADER, sessionId);

      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

      String url = itemMsUrl + "/" + id;

      ResponseEntity<ItemModel> response = restTemplate.exchange(
              url, HttpMethod.GET, requestEntity, ItemModel.class);

      return response.getBody();

    } catch (RestClientException e) {

      e.printStackTrace();
      throw new RuntimeException("Erro ao validar usuário!");

    }

  }

  @Override
  public ItemModel removeItem(Long id, String sessionId) {

    try {

      HttpHeaders headers = new HttpHeaders();
      headers.set(SESSION_ID_HEADER, sessionId);

      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

      String url = itemMsUrl + "/" + id;

      ResponseEntity<ItemModel> response = restTemplate.exchange(
              url, HttpMethod.DELETE, requestEntity, ItemModel.class);

      return response.getBody();

    } catch (RestClientException e) {

      e.printStackTrace();
      throw new RuntimeException("Erro ao validar usuário!");

    }

  }

  @Override
  public ItemModel updateItem(Long id, ItemModel itemModel, String sessionId) {

    try {

      HttpHeaders headers = new HttpHeaders();
      headers.set(SESSION_ID_HEADER, sessionId);

      HttpEntity<ItemModel> requestEntity = new HttpEntity<>(itemModel, headers);

      String url = itemMsUrl + "/" + id;

      ResponseEntity<ItemModel> response = restTemplate
              .exchange(
                      url, HttpMethod.PUT, requestEntity, ItemModel.class
              );

      return response.getBody();

    } catch (RestClientException e) {

      e.printStackTrace();
      throw new RuntimeException("Erro ao validar usuário!");

    }

  }

}