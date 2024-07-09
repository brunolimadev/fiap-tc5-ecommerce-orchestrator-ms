package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSessionDataRequest {
    private String sessionId;
    private String objectKey;
    private Object sessionData;
}
