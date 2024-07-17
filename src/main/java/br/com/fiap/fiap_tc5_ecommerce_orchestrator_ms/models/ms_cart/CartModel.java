package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_cart;

import java.util.List;

public record CartModel(
                String id,
                Double totalOrder,
                List<CartItemModel> items) {
}