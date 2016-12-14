package com.trvajjala.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.trvajjala.security.exception.JwtTokenMissingException;
import com.trvajjala.security.token.JwtAuthenticationToken;

/**
 * Filter that orchestrates authentication by using supplied JWT token
 *
 * @author ThirupathiReddy V
 */
public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

    /** Reference to logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    public JwtAuthenticationTokenFilter() {
        super("/**");
    }

    /**
     * Attempt to authenticate request - basically just pass over to another method to authenticate request headers
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        final String header = request.getHeader(tokenHeader);

        if (header == null || !header.startsWith("Bearer ")) {
            LOGGER.info("No JWT token found in request headers. token should starts with Bearer");
            throw new JwtTokenMissingException("No JWT token found in request headers. token should starts with Bearer");
        }

        final String authToken = header.substring(7);

        final JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);

        return getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * Make sure the rest of the filter chain is satisfied
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        LOGGER.info("Authentication successful");
        // As this authentication is in HTTP header, after success we need to continue the request normally
        // and return the response as if the resource was not secured at all
        chain.doFilter(request, response);
    }
}