package org.example.listaobecnosci;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "grupa")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Grupa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nazwa;

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNazwa() {
        return nazwa;
    }


}
