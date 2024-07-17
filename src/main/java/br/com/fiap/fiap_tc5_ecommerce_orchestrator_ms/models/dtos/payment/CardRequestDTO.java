package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.payment;

public record CardRequestDTO(

        String type,

        String number,

        String expiringDate,

        String verificationCode

) {}
