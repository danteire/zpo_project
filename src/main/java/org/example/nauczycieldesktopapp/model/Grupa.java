package org.example.nauczycieldesktopapp.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Grupa {

    private Long id;
    private String nazwa;

    private transient StringProperty nazwaProperty;

    public Grupa() {}

    public Grupa(String nazwa) {
        this.nazwa = nazwa;
    }

    public long getId() {
        return id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public StringProperty nazwaProperty() {
        if (nazwaProperty == null) nazwaProperty = new SimpleStringProperty(nazwa);
        return nazwaProperty;
    }
}
