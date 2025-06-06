package org.example.nauczycieldesktopapp.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.example.nauczycieldesktopapp.service.StudentService;

import java.io.IOException;

/**
 * Klasa reprezentująca obecność studenta na danym terminie (np. zajęciach).
 * Zawiera informacje o statusie obecności, powiązanym studencie oraz terminie.
 */
public class Obecnosc {

    /** Unikalny identyfikator obecności */
    private Long id;

    /** Status obecności (np. obecny, nieobecny itp.) */
    private Status status;

    /** Identyfikator studenta */
    private Long studentId;

    /** Identyfikator terminu */
    private Long terminID;

    /** Obiekt reprezentujący studenta (powiązanie) */
    private Student student;

    /** Obiekt reprezentujący termin (powiązanie) */
    private Termin termin;

    /** Property statusu obecności do wiązania w JavaFX */
    private transient StringProperty statusProperty;

    /**
     * Zwraca termin powiązany z obecnością.
     *
     * @return termin
     */
    public Termin getTermin() {
        return termin;
    }

    /**
     * Ustawia termin powiązany z obecnością.
     *
     * @param termin termin
     */
    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    /**
     * Zwraca studenta powiązanego z obecnością.
     *
     * @return student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Ustawia studenta powiązanego z obecnością.
     *
     * @param student student
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Zwraca property statusu obecności do wiązania danych w JavaFX.
     * Jeśli property jest nullem, zostaje utworzone i zainicjalizowane aktualnym statusem.
     *
     * @return property statusu obecności
     */
    public StringProperty statusProperty() {
        if (statusProperty == null && status != null) {
            statusProperty = new SimpleStringProperty(status.toString());
        }
        return statusProperty;
    }

    /**
     * Zwraca identyfikator obecności.
     *
     * @return id obecności
     */
    public Long getId() {
        return id;
    }

    /**
     * Ustawia identyfikator obecności.
     *
     * @param id identyfikator obecności
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Zwraca status obecności.
     *
     * @return status obecności
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Ustawia status obecności.
     *
     * @param status status obecności
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Zwraca identyfikator studenta powiązanego z obecnością.
     *
     * @return id studenta
     */
    public Long getStudentId() {
        return studentId;
    }

    /**
     * Ustawia identyfikator studenta powiązanego z obecnością.
     *
     * @param studentId id studenta
     */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    /**
     * Zwraca identyfikator terminu powiązanego z obecnością.
     *
     * @return id terminu
     */
    public Long getTerminID() {
        return terminID;
    }

    /**
     * Ustawia identyfikator terminu powiązanego z obecnością.
     *
     * @param terminID id terminu
     */
    public void setTerminID(Long terminID) {
        this.terminID = terminID;
    }

    /**
     * Pobiera obiekt studenta na podstawie podanego identyfikatora.
     *
     * @param studentId identyfikator studenta
     * @return obiekt studenta
     * @throws IOException w przypadku problemów z pobraniem danych
     */
    public Student getStudent(Long studentId) throws IOException {
        StudentService studentService = new StudentService();
        return (Student) studentService.getStudentsByID(studentId);
    }
}
