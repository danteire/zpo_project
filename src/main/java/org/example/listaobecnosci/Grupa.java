package org.example.listaobecnosci;

import jakarta.persistence.*;

@Entity
@Table(name = "grupa")
public class Grupa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nazwa;

    public Long getId() {
        return this.id;
    }
}
