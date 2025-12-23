package ru.rmntim.web.beans;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Named("clockBean")
@ApplicationScoped
public class ClockBean implements Serializable {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public String getCurrentDate() {
        return LocalDateTime.now().format(DATE_FORMATTER);
    }

    public String getCurrentTime() {
        return LocalDateTime.now().format(TIME_FORMATTER);
    }

    // Метод для обновления времени через AJAX
    public void updateTime() {
        // Просто обновляем время - JSF автоматически вызовет геттеры
        System.out.println("🕒 Часы обновлены: " + getCurrentTime());
    }
}