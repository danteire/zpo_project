package org.example.nauczycieldesktopapp.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Klasa reprezentująca Grupę w aplikacji.
 * <p>
 * Przechowuje identyfikator oraz nazwę grupy.
 * Udostępnia również property nazwy do celów wiązania danych w JavaFX.
 */
public class Grupa {

    /** Unikalny identyfikator grupy */
    private Long id;

    /** Nazwa grupy */
    private String nazwa;

    /** Property nazwy grupy (transient, do wiązania w JavaFX) */
    private transient StringProperty nazwaProperty;

    /**
     * Konstruktor domyślny.
     */
    public Grupa() {}

    /**
     * Konstruktor tworzący grupę o podanej nazwie.
     *
     * @param nazwa nazwa grupy
     */
    public Grupa(String nazwa) {
        this.nazwa = nazwa;
    }

    /**
     * Zwraca identyfikator grupy.
     *
     * @return id grupy
     */
    public long getId() {
        return id;
    }

    /**
     * Zwraca nazwę grupy.
     *
     * @return nazwa grupy
     */
    public String getNazwa() {
        return nazwa;
    }

    /**
     * Ustawia identyfikator grupy.
     *
     * @param id identyfikator grupy
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Zwraca właściwość nazwy grupy do użycia w wiązaniu danych w JavaFX.
     * Jeśli property jest nullem, zostaje utworzone i zainicjalizowane aktualną nazwą.
     *
     * @return property nazwy grupy
     */
    public StringProperty nazwaProperty() {
        if (nazwaProperty == null) {
            nazwaProperty = new SimpleStringProperty(nazwa);
        }
        return nazwaProperty;
    }
}
