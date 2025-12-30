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

    public AttemptBean() {
        System.out.println("[AttemptBean] Создан новый бин");
    }

    // Геттеры и сеттеры
    public Double getX() {
        System.out.println("[AttemptBean] getX: " + x);
        return x;
    }

    public void setX(Double x) {
        System.out.println("[AttemptBean] setX: " + x);
        this.x = x;
    }

    public Double getY() {
        System.out.println("[AttemptBean] getY: " + y);
        return y;
    }

    public void setY(Double y) {
        System.out.println("[AttemptBean] setY: " + y);
        this.y = y;
    }

    public Double getR() {
        System.out.println("[AttemptBean] getR: " + r);
        return r;
    }

    public void setR(Double r) {
        System.out.println("[AttemptBean] setR: " + r);
        this.r = r;
    }

    public void reset() {
        System.out.println("[AttemptBean] reset");
        this.x = null;
        this.y = null;
        this.r = 2.0;
    }
}