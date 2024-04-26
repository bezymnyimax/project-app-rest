package ru.gorbunov.dao;

import ru.gorbunov.entity.Person;

import java.sql.SQLException;
import java.sql.Statement;

public interface PhoneDAO {
    void createPhone(Person person, Statement statement) throws SQLException;

    void updatePhone(Person person, Statement statement) throws SQLException;
}
