package ru.gorbunov.dao;

import ru.gorbunov.entity.Person;

import java.sql.SQLException;

public interface PersonDAO {
    void createPerson(Person person) throws SQLException;
    Person getPersonById(long id) throws SQLException;
    Person updatePerson(Person person) throws SQLException;
    boolean deletePerson(long id) throws SQLException;

}
