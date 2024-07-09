package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.AuthDto;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.dtos.user.SignInResponseDto;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_session.CreateSessionRequest;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_user.GetUserByEmailResponse;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.JwtService;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.SessionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SessionService sessionService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
            SessionService sessionService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.sessionService = sessionService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody AuthDto credentials) throws Exception {

        // Cria instancia de autenticação com os dados do usuário
        var userCredentials = new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password());

        // Autentica o usuário
        var authentication = authenticationManager.authenticate(userCredentials);

        // Gera a sessão
        var session = sessionService.createSession(new CreateSessionRequest(credentials.email()));

        // Gera o token
        var jwt = jwtService.generateToken((GetUserByEmailResponse) authentication.getPrincipal(),
                session.getSessionId());

        // Adiciona o token no header da resposta
        HttpHeaders hearders = new HttpHeaders();

        // Adiciona a header Authorization com o token
        hearders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

        // Cria o objeto de respota com o status e o nome do usuário
        SignInResponseDto response = new SignInResponseDto(true,
                ((GetUserByEmailResponse) authentication.getPrincipal()).getName());

        return ResponseEntity.ok().headers(hearders).body(response);
    }
}
