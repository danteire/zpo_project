package org.example.nauczycieldesktopapp.model;

public class Grupa {

    private Long id;
    private String nazwa;

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
}
