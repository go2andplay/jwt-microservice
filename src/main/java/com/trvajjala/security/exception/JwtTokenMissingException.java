package com.trvajjala.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when token cannot be found in the request header
 * 
 * @author pascal alma
 */

public class JwtTokenMissingException extends AuthenticationException {

    /**
     *
     */
    private static final long serialVersionUID = -2118340456777441530L;

    public JwtTokenMissingException(String msg) {
        super(msg);
    }
}
