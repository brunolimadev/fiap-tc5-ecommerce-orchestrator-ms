package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_item;

import java.time.LocalDateTime;

public record ItemModel(
        Long id,
        String description,
        Double price,
        Integer storeQuantity,
        LocalDateTime createDateTime,
        LocalDateTime updateDateTime
) {}