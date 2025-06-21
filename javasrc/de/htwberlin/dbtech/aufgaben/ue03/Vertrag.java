package de.htwberlin.dbtech.aufgaben.ue03;

import java.time.LocalDate;

public class Vertrag {
    private Integer id;
    private Integer produktId;
    private Integer kundenId;
    private LocalDate versicherungsbeginn;
    private LocalDate versicherungsende;

    public Vertrag(Integer id, Integer produktId, Integer kundenId, LocalDate versicherungsbeginn, LocalDate versicherungsende) {
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

    public LocalDate getVersicherungsbeginn() {
        return versicherungsbeginn;
    }

    public void setVersicherungsbeginn(LocalDate versicherungsbeginn) {
        this.versicherungsbeginn = versicherungsbeginn;
    }

    public LocalDate getVersicherungsende() {
        return versicherungsende;
    }

    public void setVersicherungsende(LocalDate versicherungsende) {
        this.versicherungsende = versicherungsende;
    }
}
