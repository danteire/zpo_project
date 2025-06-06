package org.example.nauczycieldesktopapp.model;

/**
 * Enum reprezentujący status obecności studenta na zajęciach.
 */
public enum Status {
    /** Brak statusu obecności (domyślny, niezdefiniowany) */
    BRAK,

    /** Student jest nieobecny na zajęciach */
    NIEOBECNY,

    /** Student jest obecny na zajęciach */
    OBECNY,

    /** Student jest spóźniony na zajęcia */
    SPOZNIONY
}
