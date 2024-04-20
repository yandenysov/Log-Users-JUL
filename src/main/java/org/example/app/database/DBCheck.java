package org.example.app.database;

import org.example.app.utils.Constants;

import java.io.File;

// Перевірка наявності файлу БД.
public class DBCheck {

    public static boolean isDBExists() {
        return !new File(Constants.DB_BASE_URL +
                Constants.DB_NAME).exists();
    }
}
