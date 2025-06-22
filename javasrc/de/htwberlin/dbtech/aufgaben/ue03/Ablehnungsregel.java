package de.htwberlin.dbtech.aufgaben.ue03;

public class Ablehnungsregel {
    private Integer deckungsartFK;
    private Integer lfdNr;
    private String rBetrag;
    private String rAlter;

    public Ablehnungsregel() {
    }

    public Ablehnungsregel(Integer deckungsartFK, Integer lfdNr, String rBetrag, String rAlter) {
        this.deckungsartFK = deckungsartFK;
        this.lfdNr = lfdNr;
        this.rBetrag = rBetrag;
        this.rAlter = rAlter;
    }

    public Integer getDeckungsartFK() {
        return deckungsartFK;
    }

    public void setDeckungsartFK(Integer deckungsartFK) {
        this.deckungsartFK = deckungsartFK;
    }

    public Integer getLfdNr() {
        return lfdNr;
    }

    public void setLfdNr(Integer lfdNr) {
        this.lfdNr = lfdNr;
    }

    public String getrBetrag() {
        return rBetrag;
    }

    public void setrBetrag(String rBetrag) {
        this.rBetrag = rBetrag;
    }

    public String getrAlter() {
        return rAlter;
    }

    public void setrAlter(String rAlter) {
        this.rAlter = rAlter;
    }
}
