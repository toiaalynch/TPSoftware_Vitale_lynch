package org.udesa.giftcard.model;

public class Token {
    private final long creationTime;
    private final Clock clock;

    public Token(Clock clock) {
        this.clock = clock;
        this.creationTime = clock.now();
    }
    public boolean isValid() {
        return clock.now() - creationTime < 5 * 60 * 1000; // 5 minutos
    }



}
