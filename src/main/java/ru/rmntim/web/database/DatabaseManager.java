package ru.rmntim.web.database;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class DatabaseManager {
    private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());

    static {
        try {
            Class.forName("org.postgresql.Driver");
            logger.info("✅ PostgreSQL JDBC Driver загружен успешно");
        } catch (ClassNotFoundException e) {
            logger.severe("❌ PostgreSQL Driver не найден: " + e.getMessage());
            throw new RuntimeException("PostgreSQL Driver не найден", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        logger.warning("⚠️ БД отключена - используем заглушку");
        // Возвращаем null - значит БД не используется
        return null;
    }

    public static void closeResources(AutoCloseable... resources) {
        // Ничего не делаем - ресурсов нет
    }
}