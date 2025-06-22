package de.htwberlin.dbtech.aufgaben.ue03;

import java.math.BigDecimal;

public class Deckung {
    private Integer vertragFK;
    private Integer deckungsartFK;
    private BigDecimal deckungsbetrag;

    public Deckung() {
    }

    public Deckung(Integer vertragFK, Integer deckungsartFK, BigDecimal deckungsbetrag) {
        this.vertragFK = vertragFK;
        this.deckungsartFK = deckungsartFK;
        this.deckungsbetrag = deckungsbetrag;
    }

    public Integer getVertragFK() {
        return vertragFK;
    }

    public void setVertragFK(Integer vertragFK) {
        this.vertragFK = vertragFK;
    }

    public Integer getDeckungsartFK() {
        return deckungsartFK;
    }

    public void setDeckungsartFK(Integer deckungsartFK) {
        this.deckungsartFK = deckungsartFK;
    }

    public BigDecimal getDeckungsbetrag() {
        return deckungsbetrag;
    }

    public void setDeckungsbetrag(BigDecimal deckungsbetrag) {
        this.deckungsbetrag = deckungsbetrag;
    }

    @Override
    public String toString() {
        return "Deckung{" +
                "vertragFK=" + vertragFK +
                ", deckungsartFK=" + deckungsartFK +
                ", deckungsbetrag=" + deckungsbetrag +
                '}';
    }
}
