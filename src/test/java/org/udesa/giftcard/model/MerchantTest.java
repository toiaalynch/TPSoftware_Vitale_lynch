package org.udesa.giftcard.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MerchantTest {
    @Test
    public void test01merchantCanApplyChargeToGiftCard() {
        Merchant zara = new Merchant("zara-key", "Zara");
        GiftCard card = new GiftCard(1000);

        zara.charge(card, 200);

        assertEquals(800, card.getSaldo());
        assertEquals("Zara", card.getMovimientos().get(0).getMerchant());
    }

    @Test
    public void test02merchantCannotChargeMoreThanBalance() {
        Merchant zara = new Merchant("zara-key", "Zara");
        GiftCard card = new GiftCard(100);

        assertThrows(SaldoInsuficienteException.class,
                () -> zara.charge(card, 200));
    }

    @Test
    public void test03merchantCanRegisterMultipleCharges() {
        Merchant starbucks = new Merchant("sb-key", "Starbucks");
        GiftCard card = new GiftCard(1000);

        starbucks.charge(card, 200);
        starbucks.charge(card, 300);

        assertEquals(500, card.getSaldo());
        assertEquals(2, card.getMovimientos().size());

        assertEquals("Starbucks", card.getMovimientos().get(0).getMerchant());
        assertEquals(200, card.getMovimientos().get(0).getMonto());

        assertEquals("Starbucks", card.getMovimientos().get(1).getMerchant());
        assertEquals(300, card.getMovimientos().get(1).getMonto());
    }


}
