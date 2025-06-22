package de.htwberlin.dbtech.aufgaben.ue03;

import java.sql.Date;

public class Kunde {
    private Integer id;
    private String name;
    private Date geburtsdatum;

    public Kunde() {
    }

    public Kunde(Integer id, String name, Date geburtsdatum) {
        this.id = id;
        this.name = name;
        this.geburtsdatum = geburtsdatum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }
}
