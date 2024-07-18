package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.payment.PaymentRequestDTO;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.payment.PaymentResponseDTO;

public interface PaymentService {

    Object processPayment(PaymentRequestDTO paymentRequestDTO, String sessionId);

}
