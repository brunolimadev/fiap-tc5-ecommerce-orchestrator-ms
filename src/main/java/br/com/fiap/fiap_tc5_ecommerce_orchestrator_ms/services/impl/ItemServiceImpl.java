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

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

  private final RestTemplate restTemplate;

  @Value("${microservices.item-ms.url}")
  private String itemMsUrl;

  public ItemServiceImpl(RestTemplate restTemplate) {

    this.restTemplate = restTemplate;

  }

  @Override
  public ItemModel createItem(ItemModel itemModel, String sessionId) {

    try {

      this.restTemplate.postForEntity(itemMsUrl, itemModel, null)
              .getHeaders()
              .add("session-id", sessionId);

      return itemModel;

    } catch (RestClientException e) {

      e.printStackTrace();
      throw new RuntimeException("Erro ao criar o item! Por favor, tente novamente.");

    }

  }

  @Override
  public List<ItemModel> getItems(String sessionId) {
    return null;
  }

  @Override
  public ItemModel getItem(Long id, String sessionId) {

    try {

      HttpHeaders headers = new HttpHeaders();
      headers.set("session-id", sessionId);

      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

      String url = new StringBuilder(itemMsUrl)
              .append("/")
              .append(id)
              .toString();

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
    return null;
  }

  @Override
  public ItemModel updateItem(Long id, ItemModel itemEntity, String sessionId) {
    return null;
  }

}