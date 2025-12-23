package ru.rmntim.web.services;

import ru.rmntim.web.models.Attempt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

@Named("areaCheck")
@ApplicationScoped  // Изменено с SessionScoped
@AreaCheckQualifier
public class AreaCheckImpl implements AreaCheck, Serializable {

    @Override
    public void checkHit(Attempt attempt) {
        long startTime = System.nanoTime();
        attempt.setCreatedAt(new Date());

        boolean hit = isInArea(attempt.getX(), attempt.getY(), attempt.getR());
        attempt.setResult(hit);
        attempt.setExecutionTime(System.nanoTime() - startTime);
    }

    private boolean isInArea(double x, double y, double r) {
        return isInRectangle(x, y, r) ||
                isInCircle(x, y, r) ||
                isInTriangle(x, y, r);
    }

    // Левый верхний: прямоугольник x: -R/2 до 0, y: 0 до R
    private boolean isInRectangle(double x, double y, double r) {
        return (x >= -r/2) && (x <= 0) &&
                (y >= 0) && (y <= r);
    }

    // Правый верхний: четверть окружности радиусом R
    private boolean isInCircle(double x, double y, double r) {
        return (x >= 0) && (y >= 0) &&
                (x * x + y * y <= r * r);
    }

    // Левый нижний: прямоугольный треугольник
    private boolean isInTriangle(double x, double y, double r) {
        return (x <= 0) && (y <= 0) &&
                (x >= -r) && (y >= -r/2) &&
                (y <= -0.5 * x - r/2);
    }
}