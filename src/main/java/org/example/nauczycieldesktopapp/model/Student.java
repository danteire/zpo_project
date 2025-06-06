package org.example.nauczycieldesktopapp.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Klasa reprezentująca studenta.
 * Zawiera podstawowe informacje o studencie, takie jak imię, nazwisko,
 * numer indeksu oraz przynależność do grupy.
 * Udostępnia właściwości JavaFX do powiązań z UI.
 */
public class Student {

    /** Unikalny identyfikator studenta */
    private Long id;

    /** Numer indeksu studenta */
    private String nrIndeksu;

    /** Imię studenta */
    private String imie;

    /** Nazwisko studenta */
    private String nazwisko;

    /** Grupa, do której należy student */
    private Grupa grupa;

    private transient StringProperty imieProperty;
    private transient StringProperty nazwiskoProperty;
    private transient StringProperty indeksProperty;

    /**
     * Konstruktor domyślny.
     */
    public Student() {}

    /**
     * Konstruktor z podstawowymi danymi studenta.
     *
     * @param imie Imię studenta
     * @param nazwisko Nazwisko studenta
     * @param nrIndeksu Numer indeksu studenta
     */
    public Student(String imie, String nazwisko, String nrIndeksu) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nrIndeksu = nrIndeksu;
    }

    /**
     * Zwraca unikalny identyfikator studenta.
     *
     * @return identyfikator studenta
     */
    public Long getId() {
        return id;
    }

    /**
     * Zwraca numer indeksu studenta.
     *
     * @return numer indeksu
     */
    public String getNrIndeksu() {
        return nrIndeksu;
    }

    /**
     * Zwraca imię studenta.
     *
     * @return imię studenta
     */
    public String getImie() {
        return imie;
    }

    /**
     * Zwraca nazwisko studenta.
     *
     * @return nazwisko studenta
     */
    public String getNazwisko() {
        return nazwisko;
    }

    /**
     * Zwraca grupę, do której należy student.
     *
     * @return grupa studenta
     */
    public Grupa getGrupa() {
        return grupa;
    }

    /**
     * Ustawia unikalny identyfikator studenta.
     *
     * @param id identyfikator studenta
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Ustawia numer indeksu studenta.
     *
     * @param nrIndeksu numer indeksu
     */
    public void setNrIndeksu(String nrIndeksu) {
        this.nrIndeksu = String.valueOf(nrIndeksu);
    }

    /**
     * Ustawia imię studenta.
     *
     * @param imie imię
     */
    public void setImie(String imie) {
        this.imie = imie;
    }

    /**
     * Ustawia nazwisko studenta.
     *
     * @param nazwisko nazwisko
     */
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    /**
     * Ustawia grupę studenta.
     *
     * @param grupa grupa
     */
    public void setGrupa(Grupa grupa) {
        this.grupa = grupa;
    }

    /**
     * Zwraca właściwość JavaFX dla imienia studenta.
     * Przydatne do powiązań z elementami UI.
     *
     * @return właściwość imienia
     */
    public StringProperty imieProperty() {
        if (imieProperty == null) imieProperty = new SimpleStringProperty(imie);
        return imieProperty;
    }

    /**
     * Zwraca właściwość JavaFX dla nazwiska studenta.
     * Przydatne do powiązań z elementami UI.
     *
     * @return właściwość nazwiska
     */
    public StringProperty nazwiskoProperty() {
        if (nazwiskoProperty == null) nazwiskoProperty = new SimpleStringProperty(nazwisko);
        return nazwiskoProperty;
    }

    /**
     * Zwraca właściwość JavaFX dla numeru indeksu studenta.
     * Przydatne do powiązań z elementami UI.
     *
     * @return właściwość numeru indeksu
     */
    public StringProperty indeksProperty() {
        if (indeksProperty == null) indeksProperty = new SimpleStringProperty(nrIndeksu);
        return indeksProperty;
    }
}
