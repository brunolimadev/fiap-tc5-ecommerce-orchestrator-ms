package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.payment;

public record PaymentRequestDTO(

        String idShoppingCart,

        CardRequestDTO cardRequestDTO

) {}
