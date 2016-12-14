package com.trvajjala.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when token cannot be parsed
 * 
 * @author pascal alma
 */
public class JwtTokenMalformedException extends AuthenticationException {

    /**
     *
     */
    private static final long serialVersionUID = -3215560902064934317L;

    public JwtTokenMalformedException(String msg) {
        super(msg);
    }
}
