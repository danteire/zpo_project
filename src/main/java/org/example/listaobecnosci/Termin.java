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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupa_id", nullable = false)
    private Grupa grupa;

    public void setGrupa(Grupa grupa) {
        this.grupa = grupa;
    }

    public Grupa getGrupa() {
        return this.grupa;
    }
}