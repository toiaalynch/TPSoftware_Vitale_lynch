package org.udesa.giftcard.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenTest {
    @Test
    public void test01newTokenIsValid() {
        Token token = new Token(new FakeClock(0));
        assertTrue(token.isValid());
    }

}
