package com.openclassroomsprojet.poseidon.service;

import com.openclassroomsprojet.poseidon.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class MyUserDetails implements UserDetails {
    private final User user;

    public MyUserDetails(User user) {
        this.user = user;
    }
    /**
     * Renvoie la liste des autorités accordées à l'utilisateur.
     *
     * Dans cet exemple, l'autorité est le rôle de l'utilisateur.
     *
     * @return La liste des autorités accordées à l'utilisateur.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.<GrantedAuthority>singletonList(new SimpleGrantedAuthority(user.getRole()));
    }

    /**
     * Renvoie le mot de passe associé à l'utilisateur.
     *
     * @return Le mot de passe de l'utilisateur.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Renvoie le nom d'utilisateur de l'utilisateur.
     *
     * @return Le nom d'utilisateur de l'utilisateur.
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Indique si le compte de l'utilisateur a expiré.
     *
     * Dans cet exemple, le compte n'expire jamais (toujours vrai).
     *
     * @return true si le compte n'a pas expiré, sinon false.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indique si le compte de l'utilisateur est verrouillé.
     *
     * Dans cet exemple, le compte n'est jamais verrouillé (toujours vrai).
     *
     * @return true si le compte n'est pas verrouillé, sinon false.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    /**
     * Indique si les informations d'identification de l'utilisateur ont expiré.
     *
     * Dans cet exemple, les informations d'identification ne expirent jamais (toujours vrai).
     *
     * @return true si les informations d'identification ne sont pas expirées, sinon false.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indique si l'utilisateur est activé.
     *
     * Dans cet exemple, l'utilisateur est toujours activé (toujours vrai).
     *
     * @return true si l'utilisateur est activé, sinon false.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}