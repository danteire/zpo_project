package org.example.listaobecnosci;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "obecnosc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Obecnosc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private statusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "termin_id", nullable = false)
    private Termin termin;
}
