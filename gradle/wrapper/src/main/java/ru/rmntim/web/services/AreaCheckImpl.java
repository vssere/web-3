package ru.rmntim.web.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("areaCheck")
@ApplicationScoped
public class AreaCheckImpl implements AreaCheck, Serializable {

    @Override
    public boolean checkHit(double x, double y, double r) {
        // Для графика, который вы рисуете:
        // 1. Левый верхний прямоугольник (-R/2 <= x <= 0, 0 <= y <= R)
        boolean inRectangle = (x >= -r/2 && x <= 0) && (y >= 0 && y <= r);

        // 2. Правый верхний четверть круга (x >= 0, y >= 0, x² + y² <= R²)
        boolean inCircle = (x >= 0 && y >= 0) && (x * x + y * y <= r * r);

        // 3. Левый нижний треугольник (вершины: (0,0), (-R,0), (0,R/2))
        // Проверка: точка внутри треугольника
        boolean inTriangle = (x <= 0 && y >= 0) &&
                (y <= (-2 * x + r)) && // правая граница
                (y >= 0) &&            // верхняя граница
                (x >= -r);             // левая граница

        return inRectangle || inCircle || inTriangle;
    }

    // Альтернативная проверка треугольника (через формулу площади)
    private boolean isPointInTriangle(double x, double y, double r) {
        // Вершины треугольника: A(0,0), B(-R,0), C(0,R/2)
        double ax = 0, ay = 0;
        double bx = -r, by = 0;
        double cx = 0, cy = r/2;

        // Вычисляем площадь полного треугольника
        double areaABC = Math.abs((bx - ax) * (cy - ay) - (cx - ax) * (by - ay)) / 2.0;

        // Вычисляем площади трех подтреугольников
        double areaPAB = Math.abs((ax - x) * (by - y) - (bx - x) * (ay - y)) / 2.0;
        double areaPBC = Math.abs((bx - x) * (cy - y) - (cx - x) * (by - y)) / 2.0;
        double areaPCA = Math.abs((cx - x) * (ay - y) - (ax - x) * (cy - y)) / 2.0;

        // Точка внутри, если сумма площадей подтреугольников равна площади основного
        return Math.abs(areaPAB + areaPBC + areaPCA - areaABC) < 0.0001;
    }

    // Упрощенная проверка треугольника (ваш вариант)
    public boolean checkHitSimplified(double x, double y, double r) {
        // 1. Левый верхний прямоугольник (-R/2 <= x <= 0, 0 <= y <= R)
        if (x >= -r/2 && x <= 0 && y >= 0 && y <= r) {
            return true;
        }

        // 2. Правый верхний четверть круга (x >= 0, y >= 0, x² + y² <= R²)
        if (x >= 0 && y >= 0 && (x * x + y * y) <= (r * r)) {
            return true;
        }

        // 3. Левый нижний треугольник (0,0), (-R,0), (0,R/2)
        // Уравнение линии от (-R,0) до (0,R/2): y = (R/2)/(R) * (x + R) = 0.5 * (x + R)
        // Для треугольника: x <= 0, y >= 0, y <= 0.5 * (x + r)
        if (x <= 0 && y >= 0 && y <= 0.5 * (x + r) && x >= -r) {
            return true;
        }

        return false;
    }
}