package org.example.listaobecnosci.AppViews;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {

    private Long id;
    private String nrIndeksu;
    private String imie;
    private String nazwisko;
    private Grupa grupa;

    public Student(String imie, String nazwisko, String nrIndeksu) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nrIndeksu = nrIndeksu;
    }

    public Student() {}

    public Long getId() {
        return id;
    }

    public String getNrIndeksu() {
        return nrIndeksu;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public Grupa getGrupa() {
        return grupa;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setNrIndeksu(String nrIndeksu) {
        this.nrIndeksu = String.valueOf(nrIndeksu);
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setGrupa(Grupa grupa) {
        this.grupa = grupa;
    }

    private transient StringProperty imieProperty;

    private transient StringProperty nazwiskoProperty;

    private transient StringProperty indeksProperty;

    public StringProperty imieProperty() {
        if (imieProperty == null) imieProperty = new SimpleStringProperty(imie);
        return imieProperty;
    }

    public StringProperty nazwiskoProperty() {
        if (nazwiskoProperty == null) nazwiskoProperty = new SimpleStringProperty(nazwisko);
        return nazwiskoProperty;
    }
    public StringProperty indeksProperty() {
        if (indeksProperty == null) indeksProperty = new SimpleStringProperty(nrIndeksu);
        return indeksProperty;
    }
}
