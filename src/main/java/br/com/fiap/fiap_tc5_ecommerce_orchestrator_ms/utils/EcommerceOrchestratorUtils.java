package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class EcommerceOrchestratorUtils {

    /**
     * Método responsável por gerar a data de expiração do token
     *
     * @return
     */
    public static Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }

    /**
     * Método responsável por gerar a data de expiração do token
     * 
     * @param minutes horas para expiração
     *
     * @return
     */
    public static Instant generateExpirationDate(int minutes) {
        return LocalDateTime.now().plusMinutes(minutes).toInstant(ZoneOffset.of("-03:00"));
    }

}
