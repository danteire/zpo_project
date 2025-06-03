package org.example.listaobecnosci;

import jakarta.persistence.*;

/**
 * Reprezentuje studenta przypisanego do konkretnej grupy.
 *
 * Klasa jest mapowana na tabelę {@code student} w bazie danych przy użyciu JPA.
 * Każdy student posiada unikalny numer indeksu, imię, nazwisko oraz relację do grupy.
 *
 * @author Patryk Paczkowski
 * @version 1.0
 */
@Entity
@Table(name = "student")
public class Student {

    /**
     * Unikalny identyfikator studenta.
     * Jest generowany automatycznie jako klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Imię studenta.
     * Wymagane pole (nie może być null).
     */
    @Column(nullable = false)
    private String imie;

    /**
     * Nazwisko studenta.
     * Wymagane pole (nie może być null).
     */
    @Column(nullable = false)
    private String nazwisko;

    /**
     * Numer indeksu studenta.
     * Musi być unikalny, ma dokładnie 6 znaków.
     */
    @Column(nullable = false, unique = true, length = 6)
    private String nrIndeksu;

    /**
     * Grupa, do której przypisany jest student.
     * Relacja wiele do jednego z encją {@link Grupa}.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grupa_id")
    private Grupa grupa;

    /**
     * Zwraca grupę przypisaną do studenta.
     *
     * @return obiekt {@link Grupa}
     */
    public Grupa getGrupa() {
        return grupa;
    }

    /**
     * Zwraca numer indeksu studenta.
     *
     * @return numer indeksu jako {@link String}
     */
    public String getNrIndeksu() {
        return nrIndeksu;
    }

    /**
     * Zwraca nazwisko studenta.
     *
     * @return nazwisko jako {@link String}
     */
    public String getNazwisko() {
        return nazwisko;
    }

    /**
     * Zwraca imię studenta.
     *
     * @return imię jako {@link String}
     */
    public String getImie() {
        return imie;
    }

    /**
     * Zwraca identyfikator studenta.
     *
     * @return identyfikator jako {@link Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Ustawia grupę przypisaną do studenta.
     *
     * @param grupa obiekt {@link Grupa}
     */
    public void setGrupa(Grupa grupa) {
        this.grupa = grupa;
    }

    /**
     * Ustawia numer indeksu studenta.
     *
     * @param nrIndeksu numer indeksu jako {@link String}
     */
    public void setNrIndeksu(String nrIndeksu) {
        this.nrIndeksu = nrIndeksu;
    }

    /**
     * Ustawia identyfikator studenta.
     *
     * @param id identyfikator jako {@link Long}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Ustawia imię studenta.
     *
     * @param imie imię jako {@link String}
     */
    public void setImie(String imie) {
        this.imie = imie;
    }

    /**
     * Ustawia nazwisko studenta.
     *
     * @param nazwisko nazwisko jako {@link String}
     */
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
}
