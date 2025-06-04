package org.example.nauczycieldesktopapp.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.example.nauczycieldesktopapp.service.StudentService;

import java.io.IOException;

public class Obecnosc {

    private Long id;
    private Status status;
    private Long studentId;
    private Long terminID;

    private Student student;
    private Termin termin;

    public Termin getTermin() {
        return termin;
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    private transient StringProperty statusProperty;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StringProperty statusProperty() {
        if (statusProperty == null) statusProperty = new SimpleStringProperty(status.toString());
        return statusProperty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getTerminID() {
        return terminID;
    }

    public void setTerminID(Long terminID) {
        this.terminID = terminID;
    }

    public Student getStudent(Long studentId) throws IOException {
        StudentService studentService = new StudentService();
        return (Student) studentService.getStudentsByID(studentId);
    }
}
