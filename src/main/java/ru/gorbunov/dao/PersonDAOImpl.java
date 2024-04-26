package ru.gorbunov.dao;


import ru.gorbunov.db.DBConnection;
import ru.gorbunov.db.DBConnectionImpl;
import ru.gorbunov.entity.Person;
import ru.gorbunov.mapper.PersonMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonDAOImpl implements PersonDAO {

    private final DBConnection dbConnection;
    private final AddressDAO addressDAO;
    private final PhoneDAO phoneDAO;
    private final PersonMapper personMapper;

    public PersonDAOImpl() {
        this.dbConnection = DBConnectionImpl.createConnectionFactory();
        this.addressDAO = new AddressDAOImpl();
        this.phoneDAO = new PhoneDAOImpl();
        this.personMapper = new PersonMapper();
    }

    @Override
    public void createPerson(Person person) throws SQLException {
        try (
                Connection connection = dbConnection.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = String.format("insert into person (name, email) " +
                            "values ('%s', '%s')",
                    person.getName(),
                    person.getEmail());
            int affectedRow = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            if (affectedRow == 0) {
                throw new SQLException("Ошибка.");
            }

            try (ResultSet personID = statement.getGeneratedKeys()) {
                if (personID.next()) {
                    person.setId(personID.getLong("id"));
                } else {
                    throw new SQLException("Нет человека с таким ID");
                }
            }
            addressDAO.createAddress(person, statement);

            phoneDAO.createPhone(person, statement);
        }
    }

    @Override
    public Person getPersonById(long id) throws SQLException {
        try (
                Connection connection = dbConnection.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = String.format("select person.id, person.name, person.email, address.id as address_id, " +
                            "address.city, address.street, phone.id as phone_id, phone.number " +
                            "from person " +
                            "left join address on person.id = address.person_id " +
                            "left join phone on person.id = phone.person_id " +
                            "where person.id = %d",
                    id);

            try (ResultSet result = statement.executeQuery(sql)) {
                return personMapper.getPersonObjectFromResultSet(result);
            }
        }
    }

    @Override
    public Person updatePerson(Person person) throws SQLException {
        if (person.getId() == null) {
            return null;
        }
        try (
                Connection connection = dbConnection.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = String.format(
                    "update person " +
                            "set name = '%s', " +
                            "email = '%s', " +
                            "where id = %d",
                    person.getName(),
                    person.getEmail(),
                    person.getId()
            );

            statement.execute(sql);

            addressDAO.updateAddress(person, statement);

            phoneDAO.updatePhone(person, statement);

            return person;
        }
    }

    @Override
    public boolean deletePerson(long id) throws SQLException {
        if (getPersonById(id) == null) {
            return false;
        }
        try (
                Connection connection = dbConnection.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String sql = String.format("delete from person where id = %d", id);
            statement.execute(sql);
            return true;
        }
    }
}
