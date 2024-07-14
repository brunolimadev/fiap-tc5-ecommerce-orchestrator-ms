package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.filters.SecurityFilter;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_user.GetUserByEmailResponse;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.JwtService;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.utils.EcommerceOrchestratorUtils;
import jakarta.servlet.http.HttpServletRequest;

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
    public String generateToken(GetUserByEmailResponse user) throws Exception {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId().toString())
                    .withClaim("role", user.getRole().getRole())
                    .withExpiresAt(EcommerceOrchestratorUtils.generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token!", e);
        }
    }

    /**
     * Método responsável por adicionar um claim [sessionId] no token JWT
     * 
     * @param token
     * 
     * @param sessionId
     * 
     * @returns token
     */
    public String addSessionIdInClaim(String token, String sessionId) throws Exception {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedToken = JWT.decode(token);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(decodedToken.getSubject())
                    .withClaim("id", decodedToken.getClaim("id").asString())
                    .withClaim("sessionId", sessionId)
                    .withClaim("role", decodedToken.getClaim("role").asString())
                    .withExpiresAt(decodedToken.getExpiresAt())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao adicionar claim [sessionId] mo token!", e);
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
            throw new RuntimeException("Erro ao validar token!", e);

        }
    }

    public String extractSessionId(String authorization) {

        var jwt = authorization.startsWith("Bearer") ? authorization.split(" ")[1] : authorization;

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(jwt)
                    .getClaim("sessionId")
                    .asString();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Erro ao extrair sessionId do token!", e);
        }
    }

}
