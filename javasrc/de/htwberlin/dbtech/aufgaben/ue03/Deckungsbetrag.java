package de.htwberlin.dbtech.aufgaben.ue03;

import java.math.BigDecimal;

public class Deckungsbetrag {
    private Integer id;
    private Integer deckungsartFK;
    private BigDecimal deckungsbetrag;

    public Deckungsbetrag() {
    }

    public Deckungsbetrag(Integer id, Integer deckungsartFK, BigDecimal deckungsbetrag) {
        this.id = id;
        this.deckungsartFK = deckungsartFK;
        this.deckungsbetrag = deckungsbetrag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
