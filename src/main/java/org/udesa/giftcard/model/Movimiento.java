package org.udesa.giftcard.model;

public class Movimiento {
    private final int monto;
    private final String merchant;

    public Movimiento(int monto, String merchant) {
        this.monto = monto;
        this.merchant = merchant;
    }
    public int getMonto() {
        return monto;
    }

    public String getMerchant() {
        return merchant;
    }

}
