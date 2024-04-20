-- ТАБЛИЦІ
-- Можливість створення таблиць БД, з метою уникнення некваліфікованих
-- дій, краще залишити за розробником.
-- Тому такий функціонал у додатку не прописуємо.
-- Попередньо, необхідно спроектувати таблиці та їх зв'язки (за потребою),
-- на основі сутностей реального світу.
-- Тут, таблиці БД створюємо через візуальний інструмент, наприклад,
-- DB Browser for SQLite.

CREATE TABLE "users" (
	"id"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"email"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);

INSERT INTO users (name, email) VALUES(?, ?);

SELECT id, name, email FROM users;

SELECT id, name, email FROM users WHERE id = ?;

UPDATE users SET name = ?,email = ? WHERE id = ?;

DELETE FROM users WHERE id = ?;

SELECT COUNT(id) FROM users WHERE id = ?;