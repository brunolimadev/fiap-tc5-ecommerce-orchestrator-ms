package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.user;

public record SignInResponseDto(
        boolean isAuthenticated,
        String name) {
}
