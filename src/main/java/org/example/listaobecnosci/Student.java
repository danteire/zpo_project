package org.example.listaobecnosci;

import jakarta.persistence.*;

@Entity
@Table(name = "student")

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imie;

    @Column(nullable = false)
    private String nazwisko;

    @Column(nullable = false, unique = true, length = 6)
    private String nrIndeksu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupa_id")
    private Grupa grupa;

    public Grupa getGrupa() {
        return grupa;
    }

    public String getNrIndeksu() {
        return nrIndeksu;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public Long getId() {
        return id;
    }

    public void setGrupa(Grupa grupa) {
        this.grupa = grupa;
    }

    public void setNrIndeksu(String nrIndeksu) {
        this.nrIndeksu = nrIndeksu;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
}
