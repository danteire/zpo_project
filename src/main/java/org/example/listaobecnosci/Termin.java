package org.example.listaobecnosci;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "termin")
public class Termin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime data;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grupa_id", nullable = false)
    private Grupa grupa;

    public LocalDateTime getData() {
        return data;
    }

    public Long getId() {
        return id;
    }

    public Grupa getGrupa() {
        return grupa;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGrupa(Grupa grupa) {
        this.grupa = grupa;
    }
}