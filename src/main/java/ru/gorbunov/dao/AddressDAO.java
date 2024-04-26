package ru.gorbunov.dao;

import ru.gorbunov.entity.Address;
import ru.gorbunov.entity.Person;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface AddressDAO {
    void createAddress(Person person, Statement statement) throws SQLException;
    void updateAddress(Person person, Statement statement) throws SQLException;
}
