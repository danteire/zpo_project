package org.example.listaobecnosci;

/**
 * Wyliczenie reprezentujące możliwy status obecności studenta na zajęciach.
 *
 * Dostępne statusy:
 * <ul>
 *   <li>{@code BRAK} – brak statusu (domyślny lub nieokreślony)</li>
 *   <li>{@code OBECNY} – student był obecny</li>
 *   <li>{@code NIEOBECNY} – student był nieobecny</li>
 *   <li>{@code SPOZNIONY} – student spóźnił się</li>
 * </ul>
 *
 * Enum może być używany np. w rejestrze obecności.
 *
 * @author Patryk Paczkowski
 * @version 1.0
 */
public enum statusEnum {
    BRAK,
    OBECNY,
    NIEOBECNY,
    SPOZNIONY
}
