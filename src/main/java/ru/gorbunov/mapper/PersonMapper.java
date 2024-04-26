package ru.gorbunov.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.gorbunov.entity.Address;
import ru.gorbunov.entity.Person;
import ru.gorbunov.entity.Phone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper {
    private final ObjectMapper jsonMapper = new ObjectMapper();

    public Person getPersonObjectFromResultSet(ResultSet resultSet) throws SQLException {
        Person person = new Person();
        while (resultSet.next()) {
            if (person.getId() == null) {
                person.setId(resultSet.getLong("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAddress(new Address(
                        resultSet.getLong("address_id"),
                        resultSet.getString("city"),
                        resultSet.getString("street")
                ));
            }
            if (resultSet.getLong("phone_id") != 0) {
                person.addPhone(new Phone(
                        resultSet.getLong("phone_id"),
                        resultSet.getInt("number")
                ));
            }
        }
        return (person.getId() == null) ? null : person;
    }

    public Person getPersonObjectFromJSON(BufferedReader reader) throws IOException {
        return jsonMapper.readValue(reader, Person.class);
    }

    public void writeJSONFromPersonObject(PrintWriter printWriter, Person person) throws IOException {
        jsonMapper.writeValue(printWriter, person);
    }
}
