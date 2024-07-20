package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.payment.PaymentRequestDto;

public interface PaymentService {

    Object processPayment(PaymentRequestDto paymentRequestDTO, String sessionId);

}
