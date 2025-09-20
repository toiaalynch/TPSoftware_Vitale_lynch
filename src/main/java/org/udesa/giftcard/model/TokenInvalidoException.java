package org.udesa.giftcard.model;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String message) {
        super(message);
    }
}
