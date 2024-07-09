package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_user;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserByEmailResponse implements UserDetails {

    private UUID id;

    private String name;

    private String email;

    private String password;

    private boolean isActive;

    private LocalDateTime createdAt;

    private UserRoleEnum role;

    /**
     * Método responsável por retornar as autoridades do usuário
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (this.role == UserRoleEnum.ROLE_ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority(
                            UserRoleEnum.ROLE_ADMIN.getRole()),
                    new SimpleGrantedAuthority(UserRoleEnum.ROLE_USER.getRole()));
        } else {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    /**
     * Método responsável por retornar o username do usuário
     *
     * @return
     */
    @Override
    public String getUsername() {
        return this.email;
    }

    /**
     * Método responsável por retornar se a conta do usuário está expirada
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Método responsável por retornar se a conta do usuário está bloqueada
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.isActive;
    }

    /**
     * Método responsável por retornar se as credenciais do usuário estão expiradas
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Método responsável por retornar se o usuário está habilitado
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return this.isActive;
    }

    /*
     * Getters and Setters
     */
    @Override
    public String getPassword() {
        return this.password;
    }
}
