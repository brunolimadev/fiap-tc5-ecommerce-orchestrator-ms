package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_item.ItemModel;

import java.util.List;

public interface ItemService {

  ItemModel createItem(ItemModel itemEntity, String sessionId);

  List<ItemModel> getItems(String sessionId);

  ItemModel getItem(Long id, String sessionId);

  ItemModel removeItem(Long id, String sessionId);

  ItemModel updateItem(Long id, ItemModel itemEntity, String sessionId);

}