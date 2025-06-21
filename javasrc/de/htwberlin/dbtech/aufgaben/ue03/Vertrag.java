package de.htwberlin.dbtech.aufgaben.ue03;

import java.sql.Date;

public class Vertrag {
    private Integer id;
    private Integer produktId;
    private Integer kundenId;
    private Date versicherungsbeginn;
    private Date versicherungsende;

    public Vertrag() {}

    public Vertrag(Integer id, Integer produktId, Integer kundenId, Date versicherungsbeginn, Date versicherungsende) {
        this.id = id;
        this.produktId = produktId;
        this.kundenId = kundenId;
        this.versicherungsbeginn = versicherungsbeginn;
        this.versicherungsende = versicherungsende;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProduktId() {
        return produktId;
    }

    public void setProduktId(Integer produktId) {
        this.produktId = produktId;
    }

    public Integer getKundenId() {
        return kundenId;
    }

    public void setKundenId(Integer kundenId) {
        this.kundenId = kundenId;
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
