package org.example.listaobecnosci;

import jakarta.persistence.*;

/**
 * Reprezentuje grupę studentów, np. grupę zajęciową.
 *
 * Klasa mapowana jest na tabelę {@code grupa} w bazie danych.
 * Grupa posiada unikalny identyfikator oraz nazwę.
 *
 * Może być powiązana z terminami oraz studentami.
 *
 * @author Patryk Paczkowski
 * @version 1.0
 */
@Entity
@Table(name = "grupa")
public class Grupa {

    /**
     * Unikalny identyfikator grupy.
     * Generowany automatycznie jako klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nazwa grupy.
     * Wymagane pole (nie może być null).
     */
    @Column(nullable = false)
    private String nazwa;

    /**
     * Ustawia nazwę grupy.
     *
     * @param nazwa nazwa grupy jako {@link String}
     */
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    /**
     * Ustawia identyfikator grupy.
     *
     * @param id identyfikator jako {@link Long}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Zwraca identyfikator grupy.
     *
     * @return identyfikator jako {@link Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Zwraca nazwę grupy.
     *
     * @return nazwa jako {@link String}
     */
    public String getNazwa() {
        return nazwa;
    }
}
