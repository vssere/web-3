package ru.rmntim.web.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("attempt")
@RequestScoped
public class AttemptBean implements Serializable {
    private Double x;
    private Double y;
    private Double r = 2.0;

    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }

    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }

    public Double getR() { return r; }
    public void setR(Double r) { this.r = r; }

    // 🔴 Этот метод вызывается из AttemptRepository после сохранения
    public void reset() {
        this.x = null;
        this.y = null;
        this.r = 2.0;
        System.out.println("🔄 AttemptBean сброшен");
    }
}