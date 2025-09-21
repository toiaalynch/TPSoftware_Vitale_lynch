package org.udesa.giftcard.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private final String username;
    private final String password;
    private final List<GiftCard> giftCards = new ArrayList<>();
    private Token currentToken; // último token emitido al loggearse


    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Token login(String username, String password, Clock clock) {
        if (this.username.equals(username) && this.password.equals(password)) {
            this.currentToken = new Token(clock);
            return currentToken;
        }
        throw new CredencialesInvalidasException("Usuario o contraseña inválidos");
    }

    public void addGiftCard(GiftCard giftCard) {
        giftCards.add(giftCard);
    }

    public List<GiftCard> claimGiftCards(Token token) {
        if (token != currentToken || !token.isValid()) {
            throw new TokenInvalidoException("El token expiró o no pertenece al usuario");
        }
        return giftCards;
    }

    public Object getUsername() {
        return username;

    }
}