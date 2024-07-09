package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_user.GetUserByEmailResponse;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.JwtService;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.utils.EcommerceOrchestratorUtils;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String ISSUER = "Ecommerce System";

    @Value("${api.security.jwt.secret}")
    private String secret;

    /**
     * Método responsável por gerar um token JWT
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public String generateToken(GetUserByEmailResponse user, String sessionId) throws Exception {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId().toString())
                    .withClaim("sessionId", sessionId)
                    .withClaim("role", user.getRole().getRole())
                    .withExpiresAt(EcommerceOrchestratorUtils.generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token!", e);
        }
    }

    /**
     * Método responsável por validar um token JWT
     *
     * @param token
     * @return
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }

}
