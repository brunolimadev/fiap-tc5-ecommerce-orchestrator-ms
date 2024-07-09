package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.AuthService;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.UserService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return this.userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Erro ao obter usuário."));
    }

}
