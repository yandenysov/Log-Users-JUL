package org.example.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Сутність об'єкта, що є записом у БД.
// Набір змінних (властивостей об'єкта) відповідає
// стовпцям у таблиці БД.
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    Long id;
    String name;
    String email;

    @Override
    public String toString() {
        return  "id " + id +
                ", " + name +
                ", e-mail: " + email + "\n";
    }
}
