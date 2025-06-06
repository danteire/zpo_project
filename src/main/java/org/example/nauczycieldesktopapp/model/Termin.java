package org.example.nauczycieldesktopapp.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

/**
 * Klasa reprezentująca termin zajęć lub wydarzenia.
 * Zawiera informacje o dacie i godzinie terminu oraz przypisaną grupę.
 * Udostępnia właściwość JavaFX dla daty w celu powiązań z interfejsem użytkownika.
 */
public class Termin {
    /** Unikalny identyfikator terminu */
    private Long id;

    /** Data i godzina terminu */
    private LocalDateTime data;

    /** Grupa przypisana do terminu */
    private Grupa grupa;

    private transient StringProperty dataProperty;

    /**
     * Zwraca właściwość JavaFX reprezentującą datę terminu jako String.
     * Przydatne do powiązań z elementami UI.
     *
     * @return właściwość daty terminu
     */
    public StringProperty dataProperty() {
        if (dataProperty == null) dataProperty = new SimpleStringProperty(data.toString());
        return dataProperty;
    }

    /**
     * Konstruktor domyślny.
     */
    public Termin() {}

    /**
     * Konstruktor tworzący termin z podanymi wartościami.
     *
     * @param id unikalny identyfikator terminu
     * @param data data i godzina terminu
     * @param grupa grupa przypisana do terminu
     */
    public Termin(Long id, LocalDateTime data, Grupa grupa) {
        this.id = id;
        this.data = data;
        this.grupa = grupa;
    }

    /**
     * Zwraca identyfikator terminu.
     *
     * @return identyfikator terminu
     */
    public Long getId() {
        return id;
    }

    /**
     * Ustawia identyfikator terminu.
     *
     * @param id identyfikator terminu
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Zwraca datę i godzinę terminu.
     *
     * @return data i godzina terminu
     */
    public LocalDateTime getData() {
        return data;
    }

    /**
     * Ustawia datę i godzinę terminu.
     *
     * @param data data i godzina terminu
     */
    public void setData(LocalDateTime data) {
        this.data = data;
    }

    /**
     * Zwraca grupę przypisaną do terminu.
     *
     * @return grupa przypisana do terminu
     */
    public Grupa getGrupa() {
        return grupa;
    }

    /**
     * Ustawia grupę przypisaną do terminu.
     *
     * @param grupa grupa przypisana do terminu
     */
    public void setGrupa(Grupa grupa) {
        this.grupa = grupa;
    }
}
