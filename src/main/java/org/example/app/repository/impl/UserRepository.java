package org.example.app.repository.impl;

import org.example.app.database.DBConn;
import org.example.app.entity.User;
import org.example.app.repository.AppRepository;
import org.example.app.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepository implements AppRepository<User> {

    private final static String TABLE_USERS = "users";
    private static final Logger LOGGER =
            Logger.getLogger(UserRepository.class.getName());

    @Override
    public String create(User user) {
        // SQL-запит.
        // ? - заповнювач (placeholder) для параметра. Навіщо?
        // Захист від SQL-ін'єкцій.
        // Ефективність. Коли використовуємо підготовлені оператори (PreparedStatement),
        // базі даних не потрібно щоразу аналізувати/компілювати SQL-запит.
        // Використовується шаблон та просто підставляються в нього значення.
        String sql = "INSERT INTO " + TABLE_USERS + " (name, email) VALUES(?, ?)";
        // PreparedStatement - підготовлений вираз (оператор), щоб уникнути SQL-ін'єкцій
        try (PreparedStatement pstmt = DBConn.connect().prepareStatement(sql)) {
            // Формування конкретних значень для певного заповнювача
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            // Виконання SQL-запиту
            pstmt.executeUpdate();
            // Повернення повідомлення при безпомилковому
            // виконанні SQL-запиту
            return Constants.DATA_INSERT_MSG;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.LOG_DB_ERROR_MSG);
            // Повернення повідомлення про помилку роботи з БД
            return e.getMessage();
        }
    }

    @Override
    public Optional<List<User>> read() {
        try (Statement stmt = DBConn.connect().createStatement()) {
            // Колекція-контейнер для даних, які читаються з БД
            List<User> list = new ArrayList<>();
            // SQL-запит
            String sql = "SELECT id, name, email FROM " + TABLE_USERS;
            // Отримання набору даних з БД через виконання SQL-запиту
            ResultSet rs = stmt.executeQuery(sql);
            // Наповнення колекції-контейнера об'єктами з БД
            while (rs.next()) {
                list.add(new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"))
                );
            }
            // Повертаємо Optional-контейнер з колецією даних
            return Optional.of(list);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.LOG_DB_ERROR_MSG);
            // Якщо помилка повертаємо порожній Optional-контейнер
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> readById(Long id) {
        // SQL-запит.
        // ? - заповнювач (placeholder) для параметра. Навіщо?
        // Захист від SQL-ін'єкцій.
        // Ефективність. Коли використовуємо підготовлені оператори (PreparedStatement),
        // базі даних не потрібно щоразу аналізувати/компілювати SQL-запит.
        // Використовується шаблон та просто підставляються в нього значення.
        String sql = "SELECT id, name, email FROM " + TABLE_USERS +
                " WHERE id = ?";
        try (PreparedStatement pst = DBConn.connect().prepareStatement(sql)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            User user = new User();
            long entityId = rs.getLong("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            if (entityId == 0 | name == null | email == null)
                user = null;
            else {
                user.setId(entityId);
                user.setName(name);
                user.setEmail(email);
            }
            // Повертаємо Optional-контейнер з об'єктом
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.LOG_DB_ERROR_MSG);
            // Якщо помилка або такого об'єкту немає в БД,
            // повертаємо порожній Optional-контейнер
            return Optional.empty();
        }
    }

    @Override
    public String update(User user) {
        // SQL-запит.
        // ? - заповнювач (placeholder) для параметра. Навіщо?
        // Захист від SQL-ін'єкцій.
        // Ефективність. Коли використовуємо підготовлені оператори (PreparedStatement),
        // базі даних не потрібно щоразу аналізувати/компілювати SQL-запит.
        // Використовується шаблон та просто підставляються в нього значення.
        String sql = "UPDATE " + TABLE_USERS + " SET name = ?,email = ? WHERE id = ?";
        // PreparedStatement - підготовлений вираз (оператор), щоб уникнути SQL-ін'єкцій
        try (PreparedStatement pstmt = DBConn.connect().prepareStatement(sql)) {
            // Формування конкретних значень для певного заповнювача
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setLong(3, user.getId());
            // Виконання SQL-запиту
            pstmt.executeUpdate();
            // Повернення повідомлення при безпомилковому
            // виконанні SQL-запиту
            return Constants.DATA_UPDATE_MSG;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.LOG_DB_ERROR_MSG);
            // Повернення повідомлення про помилку роботи з БД
            return e.getMessage();
        }
    }

    @Override
    public String delete(Long id) {
        // SQL-запит.
        // ? - заповнювач (placeholder) для параметра. Навіщо?
        // Захист від SQL-ін'єкцій.
        // Ефективність. Коли використовуємо підготовлені оператори (PreparedStatement),
        // базі даних не потрібно щоразу аналізувати/компілювати SQL-запит.
        // Використовується шаблон та просто підставляються в нього значення.
        String sql = "DELETE FROM " + TABLE_USERS + " WHERE id = ?";
        // PreparedStatement - підготовлений вираз (оператор), щоб уникнути SQL-ін'єкцій
        try (PreparedStatement stmt = DBConn.connect().prepareStatement(sql)) {
            // Встановлення відповідних параметрів
            stmt.setLong(1, id);
            // Виконання SQL-запиту
            stmt.executeUpdate();
            // Повернення повідомлення при безпомилковому
            // виконанні SQL-запиту
            return Constants.DATA_DELETE_MSG;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.LOG_DB_ERROR_MSG);
            // Повернення повідомлення про помилку роботи з БД
            return e.getMessage();
        }
    }

    // Перевірка наявності певного id у БД
    public boolean isIdExists(Long id) {
        String sql = "SELECT COUNT(id) FROM " + TABLE_USERS +
                " WHERE id = ?";
        try {
            PreparedStatement pst = DBConn.connect().prepareStatement(sql);
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                // Очікуємо лише один результат
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.LOG_DB_ERROR_MSG);
            return false;
        }
        return false;
    }
}