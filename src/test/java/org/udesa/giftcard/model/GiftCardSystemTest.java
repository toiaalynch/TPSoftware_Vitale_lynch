package org.udesa.giftcard.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GiftCardSystemTest {

    @Test
    public void test01userCanLoginAndClaimGiftCards() {
        FakeClock clock = new FakeClock(0);
        GiftCardSystem system = new GiftCardSystem(clock);

        Token token = system.login("maika", "1234");

        List<GiftCard> cards = system.claimGiftCards(token);

        assertEquals(2, cards.size());
        assertEquals(1000, cards.get(0).getSaldo());
        assertEquals(500, cards.get(1).getSaldo());
    }

    @Test
    public void test02merchantCanChargeUserGiftCard() {
        FakeClock clock = new FakeClock(0);
        GiftCardSystem system = new GiftCardSystem(clock);

        Token token = system.login("maika", "1234");
        GiftCard card = system.claimGiftCards(token).get(0); // la primera tarjeta

        system.charge("zara-key", card, 200);

        assertEquals(800, card.getSaldo());
        assertEquals("Zara", card.getMovimientos().get(0).getMerchant());
        assertEquals(200, card.getMovimientos().get(0).getMonto());
    }

    @Test
    public void test03invalidMerchantCannotChargeGiftCard() {
        FakeClock clock = new FakeClock(0);
        GiftCardSystem system = new GiftCardSystem(clock);

        Token token = system.login("maika", "1234");
        GiftCard card = system.claimGiftCards(token).get(0);

        assertThrows(MerchantInvalidoException.class,
                () -> system.charge("fake-key", card, 200));
    }

    @Test
    public void test04fullFlowUserLoginClaimAndMerchantCharges() {
        FakeClock clock = new FakeClock(0);
        GiftCardSystem system = new GiftCardSystem(clock);

        // 1. Usuario se loggea
        Token token = system.login("maika", "1234");
        assertTrue(token.isValid());

        // 2. Reclama sus gift cards
        List<GiftCard> cards = system.claimGiftCards(token);
        assertEquals(2, cards.size());
        GiftCard card = cards.get(0);

        // 3. Un merchant válido hace dos cargos
        system.charge("zara-key", card, 200);
        system.charge("sb-key", card, 300);

        assertEquals(500, card.getSaldo());
        assertEquals(2, card.getMovimientos().size());

        assertEquals("Zara", card.getMovimientos().get(0).getMerchant());
        assertEquals(200, card.getMovimientos().get(0).getMonto());

        assertEquals("Starbucks", card.getMovimientos().get(1).getMerchant());
        assertEquals(300, card.getMovimientos().get(1).getMonto());

        // 4. Avanza el tiempo → token vence
        clock.advance(6 * 60 * 1000);

        // 5. Usuario ya no puede reclamar tarjetas con token vencido
        assertThrows(TokenInvalidoException.class,
                () -> system.claimGiftCards(token));
    }


}
