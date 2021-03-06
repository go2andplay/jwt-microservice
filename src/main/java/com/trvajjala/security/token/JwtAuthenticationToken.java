package com.trvajjala.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Holder for JWT token from the request.
 * <p/>
 * Other fields aren't used but necessary to comply to the contracts of AbstractUserDetailsAuthenticationProvider
 *
 * @author ThirupathiReddy V
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    /**
     *
     */
    private static final long serialVersionUID = 7672767151410621898L;
    private final String token;

    public JwtAuthenticationToken(String token) {
        super(null, null);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}