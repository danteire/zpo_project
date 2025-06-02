package org.example.nauczycieldesktopapp.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class Termin {
    private Long id;
    private LocalDateTime data;
    private Grupa grupa;

    private transient StringProperty dataProperty;

    public StringProperty dataProperty() {
        if (dataProperty == null) dataProperty = new SimpleStringProperty(data.toString());
        return dataProperty;
    }

    public Termin() {}
    public Termin(Long id, LocalDateTime data, Grupa grupa) {
        this.id = id;
        this.data = data;
        this.grupa = grupa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Grupa getGrupa() {
        return grupa;
    }

    public void setGrupa(Grupa grupa) {
        this.grupa = grupa;
    }
}
