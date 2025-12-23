package ru.rmntim.web.models;

import java.io.Serializable;
import java.util.Date;

public class Attempt implements Serializable {
    private Long id;
    private Double x;
    private Double y;
    private Double r;
    private Boolean result;
    private Date createdAt;
    private Long executionTime;

    // Конструктор по умолчанию
    public Attempt() {}

    // Конструктор с параметрами
    public Attempt(Double x, Double y, Double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.createdAt = new Date();
    }

    public Attempt(double v, double v1, double v2, boolean b, long l, int i) {
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public String toString() {
        return String.format(
                "Attempt{id=%d, x=%.2f, y=%.2f, r=%.2f, result=%s, time=%d ns}",
                id, x, y, r, result, executionTime
        );
    }
}