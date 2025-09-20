package org.udesa.giftcard.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenTest {

    @Test
    public void test01newTokenIsValid() {
        assertTrue(new Token(new FakeClock(0)).isValid());
    }

    @Test
    public void test02tokenIsValidBefore5Minutes() {
        FakeClock clock = new FakeClock(0);
        clock.advance(4 * 60 * 1000); // avanzo 4 minutos
        assertTrue(new Token(clock).isValid());
    }

    @Test
    public void test03tokenIsInvalidAfter5Minutes() {
        FakeClock clock = new FakeClock(0);
        Token token = new Token(clock);
        clock.advance(6 * 60 * 1000); // avanzo 6 minutos
        assertFalse(token.isValid());
    }


}
