package org.wjanaszek.checkstory.security.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenBasedAuthentication extends AbstractAuthenticationToken {

    @Getter @Setter
    private String token;
    private final UserDetails principle;

    public TokenBasedAuthentication(UserDetails principle) {
        super(principle.getAuthorities());
        this.principle = principle;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public UserDetails getPrincipal() {
        return principle;
    }

}
