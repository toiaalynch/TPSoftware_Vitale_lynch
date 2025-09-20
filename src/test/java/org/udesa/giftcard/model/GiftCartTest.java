package org.udesa.giftcard.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GiftCartTest {

    @Test
    public void test01newGiftCardWithZeroBalanceIsEmpty() {
        assertTrue(new GiftCard(0).isEmpty());
    }

    @Test
    public void test02newGiftCardWithPositiveBalanceIsNotEmpty() {
        assertFalse(new GiftCard(100).isEmpty());
    }

    @Test
    public void test03newGiftCardStartsWithInitialBalance() {
        assertEquals(1000, new GiftCard(1000).getSaldo());
    }

    @Test
    public void test04applyingChargeReducesBalance() {
        GiftCard gc = new GiftCard(1000);
        gc.aplicarCargo(200, "Zara");
        assertEquals(800, gc.getSaldo());
    }

    @Test
    public void test05applyingChargeRegistersMovement() {
        GiftCard gc = new GiftCard(1000);
        gc.aplicarCargo(200, "Zara");

        assertEquals(1, gc.getMovimientos().size());
        assertEquals("Zara", gc.getMovimientos().get(0).getMerchant());
        assertEquals(200, gc.getMovimientos().get(0).getMonto());
    }

    @Test
    public void test06cannotApplyChargeGreaterThanBalance() {
        assertThrows(SaldoInsuficienteException.class,
                () -> new GiftCard(100).aplicarCargo(200, "Zara"));
    }

}
