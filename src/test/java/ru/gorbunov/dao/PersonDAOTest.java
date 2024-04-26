package ru.gorbunov.dao;


import org.junit.*;

import ru.gorbunov.entity.Address;
import ru.gorbunov.entity.Person;
import ru.gorbunov.entity.Phone;

import java.util.Arrays;

import static org.junit.Assert.*;

public class PersonDAOTest {

    private final PersonDAO personDAO = new PersonDAOImpl();
    private Person person;

    @Before
    public void setUpLocal() throws Exception {
        person = new Person(
                null,
                "Name",
                "Email",
                new Address(null, "City", "Street"),
                Arrays.asList(
                        new Phone(null, 33333),
                        new Phone(null, 22222)
                )
        );
    }

    @Test
    public void whenUseCreatePersonThenPersonAddInDB() throws Exception {
        personDAO.createPerson(person);

        Person actualPerson = personDAO.getPersonById(person.getId());

        assertEquals(person, actualPerson);
    }

    @Test
    public void returnNullIfPersonDoesNotExist() throws Exception {
        Person person = personDAO.getPersonById(11L);
        assertNull(person);
    }

    @Test
    public void whenPersonIdIsNullTHenUpdatePersonReturnNull() throws Exception {
        Person updatedPerson = personDAO.updatePerson(person);

        assertNull(updatedPerson);
    }

    @Test
    public void ifPersonInDBTHenDeletePersonWillRemovePersonFromDB() throws Exception {
        personDAO.createPerson(person);

        assertEquals(personDAO.getPersonById(person.getId()), person);

        personDAO.deletePerson(person.getId());

        assertNull(personDAO.getPersonById(person.getId()));
    }

}