package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_cart;

public record CartItemModel(
        Long id,
        String description,
        Double price,
        Integer quantity
) {}