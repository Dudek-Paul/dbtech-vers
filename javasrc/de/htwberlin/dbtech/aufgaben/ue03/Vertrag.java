package de.htwberlin.dbtech.aufgaben.ue03;

import java.sql.Date;

public class Vertrag {
    private Integer id;
    private Integer produktFK;
    private Integer kundeFK;
    private Date versicherungsbeginn;
    private Date versicherungsende;

    public Vertrag() {}

    public Vertrag(Integer id, Integer produktFK, Integer kundeFK, Date versicherungsbeginn, Date versicherungsende) {
        this.id = id;
        this.produktFK = produktFK;
        this.kundeFK = kundeFK;
        this.versicherungsbeginn = versicherungsbeginn;
        this.versicherungsende = versicherungsende;
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

    public Integer getKundeFK() {
        return kundeFK;
    }

    public void setKundeFK(Integer kundeFK) {
        this.kundeFK = kundeFK;
    }

    public Date getVersicherungsbeginn() {
        return versicherungsbeginn;
    }

    public void setVersicherungsbeginn(Date versicherungsbeginn) {
        this.versicherungsbeginn = versicherungsbeginn;
    }

    public Date getVersicherungsende() {
        return versicherungsende;
    }

    public void setVersicherungsende(Date versicherungsende) {
        this.versicherungsende = versicherungsende;
    }
}
