package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_cart.CartItemModel;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_cart.CartModel;

public interface CartService {

  CartModel addCartItem(CartItemModel cartItemModel, String sessionId);

  CartModel removeCartItem(Long id, String sessionId);

}