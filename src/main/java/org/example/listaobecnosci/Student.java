package org.example.listaobecnosci;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @JoinColumn(name = "grupa_id", nullable = false)
    private Grupa grupa;
}
