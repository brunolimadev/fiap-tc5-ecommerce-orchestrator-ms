package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.filters;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.AuthService;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.JwtService;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final AuthService authService;

    private final SessionService sessionService;

    public SecurityFilter(JwtService jwtService, AuthService authService, SessionService sessionService) {
        this.jwtService = jwtService;
        this.authService = authService;
        this.sessionService = sessionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (this.getToken(request) != null) {

            var token = this.getToken(request);

            var email = jwtService.validateToken(token);

            UserDetails user = authService.loadUserByUsername(email);

            var sessionId = jwtService.extractSessionId(token);

            var revokedToken = sessionService.getRevokedToken(sessionId);

            if (revokedToken != null) {
                throw new RuntimeException("Sessão inválida");
            }

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continua a requisição para o próximo filtro
        filterChain.doFilter(request, response);

    }

    /**
     * Método responsável por obter o token do header da requisição
     *
     * @param request
     * @return
     */
    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer")) {
            return authorization.split(" ")[1];
        }

        return null;
    }

}
