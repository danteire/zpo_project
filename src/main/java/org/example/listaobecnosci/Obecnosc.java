package org.example.listaobecnosci;

import jakarta.persistence.*;

@Entity
@Table(name = "obecnosc")
public class Obecnosc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'BRAK'")
    private statusEnum status = statusEnum.BRAK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "termin_id", nullable = false)
    private Termin termin;

    public Long getId() {
        return id;
    }

    public statusEnum getStatus() {
        return status;
    }

    public Student getStudent() {
        return student;
    }

    public Termin getTermin() {
        return termin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setStatus(statusEnum status) {
        this.status = status;
    }
}
