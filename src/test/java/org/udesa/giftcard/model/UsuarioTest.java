package org.udesa.giftcard.model;
import java.util.List;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    public void test01validUserLoginReturnsValidToken() {
        FakeClock clock = new FakeClock(0);
        Usuario user = new Usuario("maika", "1234");
        Token token = user.login("maika", "1234", clock);
        assertTrue(token.isValid());
    }

    @Test
    public void test02invalidUserLoginThrowsException() {
        FakeClock clock = new FakeClock(0);
        Usuario user = new Usuario("maika", "1234");

        assertThrows(CredencialesInvalidasException.class,
                () -> user.login("maika", "wrongpass", clock));
    }

    @Test
    public void test03userCanClaimGiftCardsWithValidToken() {
        FakeClock clock = new FakeClock(0);
        Usuario user = new Usuario("maika", "1234");
        user.addGiftCard(new GiftCard(1000));
        user.addGiftCard(new GiftCard(500));

        Token token = user.login("maika", "1234", clock);

        List<GiftCard> cards = user.claimGiftCards(token);
        assertEquals(2, cards.size());
        assertEquals(1000, cards.get(0).getSaldo());
        assertEquals(500, cards.get(1).getSaldo());
    }

    @Test
    public void test04cannotClaimGiftCardsWithInvalidToken() {
        FakeClock clock = new FakeClock(0);
        Usuario user = new Usuario("maika", "1234");
        user.addGiftCard(new GiftCard(1000));

        Token token = user.login("maika", "1234", clock);
        clock.advance(6 * 60 * 1000); // avanzo 6 minutos → token vence

        assertThrows(TokenInvalidoException.class,
                () -> user.claimGiftCards(token));
    }
    @Test
    public void test05userWithoutGiftCardsClaimsEmptyList() {
        FakeClock clock = new FakeClock(0);
        Usuario user = new Usuario("maika", "1234");
        Token token = user.login("maika", "1234", clock);
        assertTrue(user.claimGiftCards(token).isEmpty());
    }
    @Test
    public void test06userCanClaimMultipleGiftCards() {
        FakeClock clock = new FakeClock(0);
        Usuario user = new Usuario("maika", "1234");
        user.addGiftCard(new GiftCard(100));
        user.addGiftCard(new GiftCard(200));
        user.addGiftCard(new GiftCard(300));

        Token token = user.login("maika", "1234", clock);

        assertEquals(3, user.claimGiftCards(token).size());
    }

    @Test
    public void test07cannotClaimWithInvalidTokenFromAnotherClock() {
        FakeClock clock1 = new FakeClock(0);
        FakeClock clock2 = new FakeClock(0);

        Usuario user = new Usuario("maika", "1234");
        user.addGiftCard(new GiftCard(1000));

        Token tokenFromOtherClock = new Token(clock2); // token ajeno al login
        assertThrows(TokenInvalidoException.class,
                () -> user.claimGiftCards(tokenFromOtherClock));
    }

    @Test
    public void test08secondLoginInvalidatesFirstToken() {
        FakeClock clock = new FakeClock(0);
        Usuario user = new Usuario("maika", "1234");
        user.addGiftCard(new GiftCard(1000));
        Token token1 = user.login("maika", "1234", clock);
        Token token2 = user.login("maika", "1234", clock);
        assertThrows(TokenInvalidoException.class,
                () -> user.claimGiftCards(token1));

        assertEquals(1, user.claimGiftCards(token2).size());
    }
}
