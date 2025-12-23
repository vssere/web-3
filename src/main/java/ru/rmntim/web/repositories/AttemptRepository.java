package ru.rmntim.web.repositories;

import ru.rmntim.web.database.DatabaseManager;
import ru.rmntim.web.models.Attempt;
import ru.rmntim.web.services.AreaCheck;
import ru.rmntim.web.beans.AttemptBean;
import ru.rmntim.web.services.AreaCheckQualifier;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Named("attemptRepository")
@SessionScoped
public class AttemptRepository implements Serializable {
    private List<Attempt> latestAttemptsList = new ArrayList<>();

    @Inject
    @AreaCheckQualifier
    private AreaCheck areaCheck;

    @Inject
    private AttemptBean attemptBean;

    // Переменные для графика
    private Double graphX;
    private Double graphY;
    private Double graphR;

    @PostConstruct
    public void init() {
        System.out.println("⚠️ AttemptRepository: Работаем без БД (заглушка)");
        // Добавим несколько тестовых записей для демонстрации
        latestAttemptsList.add(new Attempt(-2.0, 1.0, 3.0, true, System.currentTimeMillis(), 1000));
        latestAttemptsList.add(new Attempt(0.0, 0.0, 2.0, true, System.currentTimeMillis() - 10000, 800));
        latestAttemptsList.add(new Attempt(1.5, -2.0, 4.0, false, System.currentTimeMillis() - 20000, 1200));
    }

    private void loadFromDatabase() {
        System.out.println("⚠️ Загрузка из БД пропущена (работаем в памяти)");
    }

    private Attempt mapResultSetToAttempt(ResultSet rs) throws SQLException {
        return new Attempt();
    }

    // 🔴 Метод вызывается из формы (кнопка "Проверить")
    public void addAttemptFromBean() {
        System.out.println("🔄 Вызван addAttemptFromBean()");

        Double x = attemptBean.getX();
        Double y = attemptBean.getY();
        Double r = attemptBean.getR();

        if (x == null || y == null || r == null) {
            System.err.println("❌ Одно из значений null: x=" + x + ", y=" + y + ", r=" + r);
            return;
        }

        System.out.println("📊 Данные из формы: x=" + x + ", y=" + y + ", r=" + r);
        addAttempt(new Attempt(x, y, r));
        attemptBean.reset();
    }

    // 🔴 Метод вызывается из графика (клик по canvas)
    public void addAttemptFromGraph() {
        System.out.println("🎯 Вызван addAttemptFromGraph()");
        System.out.println("📊 Данные с графика: x=" + graphX + ", y=" + graphY + ", r=" + graphR);

        if (graphX == null || graphY == null || graphR == null) {
            System.err.println("❌ Одно из значений null");
            return;
        }

        // Округляем X до ближайшего допустимого значения
        double[] validX = {-2.0, -1.5, -1.0, -0.5, 0.0, 0.5, 1.0, 1.5};
        double closestX = validX[0];
        double minDiff = Math.abs(graphX - closestX);

        for (double val : validX) {
            double diff = Math.abs(graphX - val);
            if (diff < minDiff) {
                minDiff = diff;
                closestX = val;
            }
        }

        // Округляем Y и ограничиваем диапазоном [-3, 3]
        int roundedY = (int) Math.round(graphY);
        int clampedY = Math.max(-3, Math.min(3, roundedY));

        System.out.println("📈 Округленные значения: x=" + closestX + ", y=" + clampedY + ", r=" + graphR);
        addAttempt(new Attempt(closestX, (double) clampedY, graphR));
    }

    // 🔴 Общий метод для добавления попытки
    public void addAttempt(Attempt attempt) {
        // Проверяем попадание
        areaCheck.checkHit(attempt);

        // Устанавливаем время
        attempt.setCreatedAt(new java.util.Date());
        attempt.setExecutionTime((long) (Math.random() * 1000 + 500));

        // Добавляем в список
        latestAttemptsList.add(0, attempt);
        if (latestAttemptsList.size() > 20) {
            latestAttemptsList.remove(latestAttemptsList.size() - 1);
        }

        System.out.println("✅ Попытка добавлена: " + attempt);
    }

    private void saveToDatabase(Attempt attempt) {
        System.out.println("⚠️ Сохранение в БД пропущено: " + attempt);
    }

    public void clearAttempts() {
        System.out.println("🧹 Очистка всех записей (работа в памяти)");
        latestAttemptsList.clear();
    }

    public List<Attempt> getLatestAttemptsList() {
        return latestAttemptsList;
    }

    // 🔴 Геттеры и сеттеры для графика
    public Double getGraphX() {
        return graphX;
    }

    public void setGraphX(Double graphX) {
        this.graphX = graphX;
    }

    public Double getGraphY() {
        return graphY;
    }

    public void setGraphY(Double graphY) {
        this.graphY = graphY;
    }

    public Double getGraphR() {
        return graphR;
    }

    public void setGraphR(Double graphR) {
        this.graphR = graphR;
    }
}