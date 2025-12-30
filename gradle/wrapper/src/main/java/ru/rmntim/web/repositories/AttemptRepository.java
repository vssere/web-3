package ru.rmntim.web.repositories;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import ru.rmntim.web.models.Attempt;
import ru.rmntim.web.services.AreaCheck;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("attemptRepository")
@SessionScoped
public class AttemptRepository implements Serializable {
    private List<Attempt> latestAttemptsList = new ArrayList<>();

    @Inject
    private AreaCheck areaCheck;

    // Текущие значения для графика
    private Double graphX;
    private Double graphY;
    private Double graphR = 2.0;

    @PostConstruct
    public void init() {
        System.out.println("[AttemptRepository] Инициализирован");

        // Тестовые данные
        latestAttemptsList.add(new Attempt(-2.0, 1.0, 3.0, true,
                System.currentTimeMillis(), 1000L));
        latestAttemptsList.add(new Attempt(0.0, 0.0, 2.0, true,
                System.currentTimeMillis() - 10000L, 800L));
    }

    // Простой метод для добавления попытки
    public void addAttemptFromBean() {
        System.out.println("[AttemptRepository] addAttemptFromBean вызван");

        // Просто добавляем тестовую попытку
        Attempt attempt = new Attempt(-2.0, 1.0, 3.0);
        addAttempt(attempt);
    }

    // Добавление попытки с графическими координатами
    public void addAttemptFromGraph() {
        System.out.println("[AttemptRepository] addAttemptFromGraph: " +
                "x=" + graphX + ", y=" + graphY + ", r=" + graphR);

        if (graphX != null && graphY != null && graphR != null) {
            Attempt attempt = new Attempt(graphX, graphY, graphR);
            addAttempt(attempt);
        }
    }

    // Основной метод добавления
    private void addAttempt(Attempt attempt) {
        boolean hit = areaCheck.checkHit(
                attempt.getX(),
                attempt.getY(),
                attempt.getR()
        );

        attempt.setResult(hit);
        attempt.setCreatedAt(new java.util.Date());
        attempt.setExecutionTime(100L); // фиксированное время

        latestAttemptsList.add(0, attempt);

        // Ограничиваем размер
        if (latestAttemptsList.size() > 20) {
            latestAttemptsList = new ArrayList<>(latestAttemptsList.subList(0, 20));
        }

        System.out.println("[AttemptRepository] Добавлена попытка: " + attempt);
    }

    // Очистка всех попыток
    public void clearAttempts() {
        System.out.println("[AttemptRepository] Очистка всех попыток");
        latestAttemptsList.clear();
    }

    // Геттеры и сеттеры
    public List<Attempt> getLatestAttemptsList() {
        System.out.println("[AttemptRepository] getLatestAttemptsList: " +
                latestAttemptsList.size() + " записей");
        return latestAttemptsList;
    }

    public void setLatestAttemptsList(List<Attempt> latestAttemptsList) {
        this.latestAttemptsList = latestAttemptsList;
    }

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

    public int getAttemptCount() {
        return latestAttemptsList.size();
    }
}