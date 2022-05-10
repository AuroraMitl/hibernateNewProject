package org.example;

import org.example.entity.BirthDay;
import org.example.entity.User;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Optional;

import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.*;

class HibernateRunnerTest {

    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .user_name("ivan3@gmail.com")
                .first_name("Ivan3")
                .last_name("Ivanov3")
                .birth_Date(new BirthDay(LocalDate.of(2000, 1, 19)))
                .build();

        String sql = """
                insert
                into
                %s
                (%s)
                values
                (%s)
                """;
        String tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(user.getClass().getName());

        Field[] declaredFields = user.getClass().getDeclaredFields();

        String columnNames = Arrays.stream(declaredFields)
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(joining(", "));

        String columnValues = Arrays.stream(declaredFields)
                .map(field -> "?")
                .collect(joining(", "));

        System.out.println(sql.formatted(tableName, columnNames, columnValues));
    }
}