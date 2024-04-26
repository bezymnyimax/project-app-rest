package ru.gorbunov.dao;

import ru.gorbunov.entity.Person;
import ru.gorbunov.entity.Phone;

import java.sql.*;


public class PhoneDAOImpl implements PhoneDAO {

    @Override
    public void createPhone(Person person, Statement statement) throws SQLException {
        String sql = "insert into phone (number, person_id)" +
                "values (%d, %d)";
        if (person.getPhone() == null) {
            return;
        }
        for (Phone phone : person.getPhone()) {
            int affectedRow = statement.executeUpdate(
                    sql.formatted(phone.getNumber(), person.getId()),
                    Statement.RETURN_GENERATED_KEYS
            );
            if (affectedRow == 0) {
                throw new SQLException("Ошибка.");
            }

            try (ResultSet accountID = statement.getGeneratedKeys()) {
                if (accountID.next()) {
                    phone.setId(accountID.getLong("id"));
                } else {
                    throw new SQLException("Нет телефона с таким ID.");
                }
            }
        }
    }

    @Override
    public void updatePhone(Person person, Statement statement) throws SQLException {
        String sql = "update phone " +
                "number = %d " +
                "where person_id = %d and id = %d";
        for (Phone phone : person.getPhone()) {
            statement.execute(sql.formatted(
                    phone.getNumber(),
                    person.getId(),
                    phone.getId()
            ));
        }
    }
}
