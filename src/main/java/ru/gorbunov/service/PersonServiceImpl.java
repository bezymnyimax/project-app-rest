package ru.gorbunov.service;

import ru.gorbunov.dao.PersonDAO;
import ru.gorbunov.dao.PersonDAOImpl;
import ru.gorbunov.entity.Person;
import ru.gorbunov.mapper.PersonMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class PersonServiceImpl implements PersonService {

    private final PersonDAO personDAO = new PersonDAOImpl();
    private final PersonMapper personMapper = new PersonMapper();

    @Override
    public void getPerson(long person_id, PrintWriter out) throws SQLException, IOException {
        Person person = personDAO.getPersonById(person_id);
        if (person == null) {
            throw new RuntimeException(String.format("ID - %d отсутствует", person_id));
        }
        personMapper.writeJSONFromPersonObject(out, person);
    }

    @Override
    public void createPerson(BufferedReader in, PrintWriter out) throws SQLException, IOException {
        Person person = personMapper.getPersonObjectFromJSON(in);
        personDAO.createPerson(person);
        personMapper.writeJSONFromPersonObject(out, person);
    }

    @Override
    public void updatePerson(BufferedReader in, PrintWriter out) throws SQLException, IOException {
        Person person = personMapper.getPersonObjectFromJSON(in);
        if (personDAO.getPersonById(person.getId()) == null) {
            throw new RuntimeException(String.format("ID - %d отсутствует", person.getId()));
        }
        personDAO.updatePerson(person);
        personMapper.writeJSONFromPersonObject(out, person);
    }

    @Override
    public void deletePerson(long person_id, PrintWriter out) throws SQLException, IOException {
        boolean result = personDAO.deletePerson(person_id);
        if (result) {
            out.print(String.format("{\"успешно\":\" ID - %d удален.\"}", person_id));
        } else {
            throw new RuntimeException(String.format("ID - %d отсутствует", person_id));
        }
    }
}
