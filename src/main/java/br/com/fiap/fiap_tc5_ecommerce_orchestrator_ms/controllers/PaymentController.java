package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.controllers;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.payment.PaymentRequestDTO;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.payment.PaymentResponseDTO;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.JwtService;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payments")
@Tag(name = "Payment Controller", description = "Customer can request payment for API resources")
public class PaymentController {

    private PaymentService paymentService;

    private JwtService jwtService;

    public PaymentController(PaymentService paymentService, JwtService jwtService) {
        this.paymentService = paymentService;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Request Payment")
    @ApiResponse(responseCode = "201", description = "Returns payment status")
    @PostMapping
    public ResponseEntity<Object> processPayment(
            @RequestBody PaymentRequestDTO requestDTO,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {

        var sessionId = jwtService.extractSessionId(jwt);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.processPayment(requestDTO, sessionId));
    }

}