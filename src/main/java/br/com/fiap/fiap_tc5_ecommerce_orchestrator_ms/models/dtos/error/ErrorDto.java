package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.error;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ErrorDto(
        String title,
        String message,
        String code,
        @JsonInclude(JsonInclude.Include.NON_NULL) List<ErrorFieldDto> fields) {
}
