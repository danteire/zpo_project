package org.example.listaobecnosci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Główna klasa uruchamiająca aplikację Spring Boot Lista Obecności.
 *
 * Punkt wejścia do aplikacji. Inicjalizuje kontekst Springa i uruchamia serwer.
 *
 * Adnotacja {@link SpringBootApplication} włącza automatyczne konfigurowanie,
 * skanowanie komponentów i inne mechanizmy Spring Boot.
 *
 * @author Patryk Paczkowski
 * @version 1.0
 */
@SpringBootApplication
public class ListaobecnosciApplication {

    /**
     * Metoda główna uruchamiająca aplikację.
     *
     * @param args argumenty przekazywane z linii poleceń
     */
    public static void main(String[] args) {
        SpringApplication.run(ListaobecnosciApplication.class, args);
    }

}
