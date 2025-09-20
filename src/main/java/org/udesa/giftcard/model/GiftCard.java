package org.udesa.giftcard.model;

import java.util.ArrayList;
import java.util.List;

public class GiftCard {
    private int saldo;
    private final List<Movimiento> movimientos = new ArrayList<>();

    public GiftCard(int saldoInicial) {
        this.saldo = saldoInicial;
    }

    public boolean isEmpty() {
        return saldo == 0;
    }

    public int getSaldo() {
        return saldo;
    }

    public void aplicarCargo(int monto, String merchant) {
        if (monto > saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }
        saldo -= monto;
        movimientos.add(new Movimiento(monto, merchant));
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;

    }
}
