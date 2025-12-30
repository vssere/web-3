package ru.rmntim.web.models;

import java.io.Serializable;
import java.util.Date;

public class Attempt implements Serializable {
    private Double x;
    private Double y;
    private Double r;
    private Boolean result;
    private Date createdAt;
    private Long executionTime;

    // Конструкторы
    public Attempt() {}

    public Attempt(Double x, Double y, Double r, Boolean result, Long timestamp, Long executionTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.createdAt = new Date(timestamp);
        this.executionTime = executionTime; // executionTime уже Long
    }

    public Attempt(Double x, Double y, Double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.createdAt = new Date();
    }

    // Геттеры и сеттеры
    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }

    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }

    public Double getR() { return r; }
    public void setR(Double r) { this.r = r; }

    public Boolean getResult() { return result; }
    public void setResult(Boolean result) { this.result = result; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Long getExecutionTime() { return executionTime; }
    public void setExecutionTime(Long executionTime) { this.executionTime = executionTime; }

    @Override
    public String toString() {
        return "Attempt{x=" + x + ", y=" + y + ", r=" + r +
                ", result=" + result + ", time=" + createdAt + "}";
    }
}