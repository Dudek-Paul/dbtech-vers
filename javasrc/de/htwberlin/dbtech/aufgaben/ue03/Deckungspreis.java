package de.htwberlin.dbtech.aufgaben.ue03;

import java.math.BigDecimal;
import java.sql.Date;

public class Deckungspreis {
    private Integer id;
    private Integer deckungsbetragFK;
    private Date gueltigVon;
    private Date gueltigBis;
    private BigDecimal preis;

    public Deckungspreis() {}

    public Deckungspreis(Integer id, Integer deckungsbetragFK, Date gueltigVon, Date gueltigBis, BigDecimal preis) {
        this.id = id;
        this.deckungsbetragFK = deckungsbetragFK;
        this.gueltigVon = gueltigVon;
        this.gueltigBis = gueltigBis;
        this.preis = preis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeckungsbetragFK() {
        return deckungsbetragFK;
    }

    public void setDeckungsbetragFK(Integer deckungsbetragFK) {
        this.deckungsbetragFK = deckungsbetragFK;
    }

    public Date getGueltigVon() {
        return gueltigVon;
    }

    public void setGueltigVon(Date gueltigVon) {
        this.gueltigVon = gueltigVon;
    }

    public Date getGueltigBis() {
        return gueltigBis;
    }

    public void setGueltigBis(Date gueltigBis) {
        this.gueltigBis = gueltigBis;
    }

    public BigDecimal getPreis() {
        return preis;
    }

    public void setPreis(BigDecimal preis) {
        this.preis = preis;
    }
}
