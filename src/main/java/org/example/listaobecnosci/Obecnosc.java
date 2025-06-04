package org.example.listaobecnosci;

import jakarta.persistence.*;

/**
 * Reprezentuje obecność studenta na konkretnym terminie zajęć.
 *
 * Każda obecność przypisana jest do konkretnego studenta i terminu.
 * Status obecności określany jest przez enum {@link statusEnum}.
 * Domyślny status to {@code BRAK}.
 *
 * Klasa mapowana na tabelę {@code obecnosc} w bazie danych.
 *
 * @author Patryk Paczkowski
 * @version 1.0
 */
@Entity
@Table(name = "obecnosc")
public class Obecnosc {

    /**
     * Unikalny identyfikator wpisu obecności.
     * Generowany automatycznie jako klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Status obecności studenta.
     * Wartość przechowywana jako ciąg znaków w bazie danych.
     * Domyślnie ustawiony na {@code BRAK}.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'BRAK'")
    private statusEnum status = statusEnum.BRAK;

    /**
     * Student przypisany do tego wpisu obecności.
     * Relacja wiele do jednego z encją {@link Student}.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    /**
     * Termin, którego dotyczy obecność.
     * Relacja wiele do jednego z encją {@link Termin}.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "termin_id", nullable = false)
    private Termin termin;

    /**
     * Zwraca identyfikator obecności.
     *
     * @return identyfikator jako {@link Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Zwraca status obecności.
     *
     * @return status jako {@link statusEnum}
     */
    public statusEnum getStatus() {
        return status;
    }

    /**
     * Zwraca studenta przypisanego do obecności.
     *
     * @return obiekt {@link Student}
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Zwraca termin przypisany do obecności.
     *
     * @return obiekt {@link Termin}
     */
    public Termin getTermin() {
        return termin;
    }

    /**
     * Ustawia identyfikator obecności.
     *
     * @param id identyfikator jako {@link Long}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Ustawia termin przypisany do obecności.
     *
     * @param termin obiekt {@link Termin}
     */
    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    /**
     * Ustawia studenta przypisanego do obecności.
     *
     * @param student obiekt {@link Student}
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Ustawia status obecności.
     *
     * @param status status jako {@link statusEnum}
     */
    public void setStatus(statusEnum status) {
        this.status = status;
    }
}
