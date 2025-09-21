package org.udesa.giftcard.model;

import java.util.ArrayList;
import java.util.List;

public class GiftCardSystem {
    private final Clock clock;
    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Merchant> merchants = new ArrayList<>();

    public GiftCardSystem(Clock clock) {
        this.clock = clock;
        precargarDatos();
    }

    private void precargarDatos() {
        Usuario maika = new Usuario("maika", "1234");
        maika.addGiftCard(new GiftCard(1000));
        maika.addGiftCard(new GiftCard(500));
        usuarios.add(maika);

        merchants.add(new Merchant("zara-key", "Zara"));
        merchants.add(new Merchant("sb-key", "Starbucks"));
    }

    public Token login(String username, String password) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equals(username)) {
                return u.login(username, password, clock);
            }
        }
        throw new CredencialesInvalidasException("Usuario no encontrado");
    }

    public List<GiftCard> claimGiftCards(Token token) {
        for (Usuario u : usuarios) {
            try {
                return u.claimGiftCards(token);
            } catch (TokenInvalidoException e) {
                // ignoramos y seguimos probando
            }
        }
        throw new TokenInvalidoException("Token inválido para todos los usuarios");
    }

    public void charge(String merchantKey, GiftCard card, int monto) {
        for (Merchant m : merchants) {
            if (m.getKey().equals(merchantKey)) {
                m.charge(card, monto);
                return;
            }
        }
        throw new MerchantInvalidoException("Merchant con clave inválida: " + merchantKey);
    }

}
