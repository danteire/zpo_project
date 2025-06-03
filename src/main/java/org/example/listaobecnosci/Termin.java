package org.example.listaobecnosci;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Reprezentuje pojedynczy termin (np. zajęć, spotkania) powiązany z określoną grupą.
 *
 * Klasa jest mapowana na tabelę {@code termin} w bazie danych przy użyciu JPA.
 *
 * @author Patryk Paczkowski
 * @version 1.0
 */
@Entity
@Table(name = "termin")
public class Termin {

    /**
     * Unikalny identyfikator terminu.
     * Jest generowany automatycznie jako klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Data i godzina terminu.
     * Pole wymagane (nie może być null).
     */
    @Column(nullable = false)
    private LocalDateTime data;

    /**
     * Grupa, do której przypisany jest termin.
     * Relacja wiele do jednego z encją {@link Grupa}.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grupa_id", nullable = false)
    private Grupa grupa;

    /**
     * Zwraca datę i godzinę terminu.
     *
     * @return data i godzina jako {@link LocalDateTime}
     */
    public LocalDateTime getData() {
        return data;
    }

    /**
     * Zwraca identyfikator terminu.
     *
     * @return identyfikator terminu jako {@link Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Zwraca grupę przypisaną do terminu.
     *
     * @return obiekt {@link Grupa}
     */
    public Grupa getGrupa() {
        return grupa;
    }

    /**
     * Ustawia datę i godzinę terminu.
     *
     * @param data data i godzina jako {@link LocalDateTime}
     */
    public void setData(LocalDateTime data) {
        this.data = data;
    }

    /**
     * Ustawia identyfikator terminu.
     *
     * @param id identyfikator jako {@link Long}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Ustawia grupę przypisaną do terminu.
     *
     * @param grupa obiekt {@link Grupa}
     */
    public void setGrupa(Grupa grupa) {
        this.grupa = grupa;
    }
}