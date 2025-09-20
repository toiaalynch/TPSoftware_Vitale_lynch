package org.udesa.giftcard.model;

public class Merchant {
    private final String key;
    private final String name;

    public Merchant(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public void charge(GiftCard card, int monto) {
        card.aplicarCargo(monto, name);
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
