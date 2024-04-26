package ru.gorbunov.dao;


import ru.gorbunov.entity.Address;
import ru.gorbunov.entity.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddressDAOImpl implements AddressDAO {


    @Override
    public void createAddress(Person person, Statement statement) throws SQLException {
        Address address = person.getAddress();
        String sql = String.format("insert into address (city, street, person_id) " +
                        "values ('%s', '%s', %d)",
                address.getCity(),
                address.getStreet(),
                person.getId());

        int affectedRow = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRow == 0) {
            throw new SQLException("Ошибка.");
        }

        try (ResultSet addressID = statement.getGeneratedKeys()) {
            if (addressID.next()) {
                address.setId(addressID.getLong("id"));
            } else {
                throw new SQLException("Нет адресса с таким ID.");
            }
        }
    }

    @Override
    public void updateAddress(Person person, Statement statement) throws SQLException {
        Address address = person.getAddress();
        String sql = String.format("update address " +
                        "city = '%s'," +
                        "street = '%s'," +
                        "where person_id = %d",
                address.getCity(),
                address.getStreet(),
                person.getId()
        );
        statement.execute(sql);
    }
}
