package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record PaymentResponseDTO(

        String idStatus,
        @JsonProperty("idProcessPayment")
        String idProcessPayment,

        LocalDateTime dateTimeStartStage,
        LocalDateTime dateTimeEndStage,
        String status,

        String idShoppingCart


) {}
