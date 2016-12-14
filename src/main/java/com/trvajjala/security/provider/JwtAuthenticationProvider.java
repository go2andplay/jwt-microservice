package com.trvajjala.security.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.trvajjala.security.exception.JwtTokenMalformedException;
import com.trvajjala.security.model.AuthenticatedUser;
import com.trvajjala.security.token.JwtAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

/**
 * Used for checking the token from the request and supply the UserDetails if the token is valid
 *
 * @author ThirupathiReddy v
 */
@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        final JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        final String token = jwtAuthenticationToken.getToken();

        try {

            final Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            final List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList((String) body.get("role"));
            return new AuthenticatedUser((String) body.get("userId"), body.getSubject(), token, authorityList);

        } catch (final JwtException e) {
            throw new JwtTokenMalformedException("JWT token is not valid");
        }
    }

}
