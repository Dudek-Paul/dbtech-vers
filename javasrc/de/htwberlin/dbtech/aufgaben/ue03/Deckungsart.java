package de.htwberlin.dbtech.aufgaben.ue03;

import java.sql.Date;

public class Deckungsart {
    private Integer id;
    private Integer produktFK;
    private String kurzBezeichnung;
    private String bezeichnung;

    public Deckungsart() {
    }

    public Deckungsart(Integer id, Integer produktFK, String kurzBezeichnung, String bezeichnung) {
        this.id = id;
        this.produktFK = produktFK;
        this.kurzBezeichnung = kurzBezeichnung;
        this.bezeichnung = bezeichnung;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProduktFK() {
        return produktFK;
    }

    public void setProduktFK(Integer produktFK) {
        this.produktFK = produktFK;
    }

    public String getKurzBezeichnung() {
        return kurzBezeichnung;
    }

    public void setKurzBezeichnung(String kurzBezeichnung) {
        this.kurzBezeichnung = kurzBezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
}
