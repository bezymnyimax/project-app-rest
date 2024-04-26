package ru.gorbunov.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PersonTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        this.mapper = new ObjectMapper();
    }

    @Test
    public void whenCreateUserWithFullConstructorThenAllFieldsAreNotNull() {
        Address address = new Address(
                456L,
                "Москва",
                "Пушкина"
        );
        List<Phone> bankAccounts = Arrays.asList(
                new Phone(789L, 12345),
                new Phone(654L, 1234567)
        );
        Person person = new Person(
                123L,
                "Вася",
                "Почта",
                new Address(
                        456L,
                        "Москва",
                        "Пушкина"
                ),
                Arrays.asList(
                        new Phone(789L, 12345),
                        new Phone(654L, 1234567)
                )
        );

        assertNotNull(person.getId());
        assertNotNull(person.getName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getPhone());

        assertEquals(Long.valueOf(123L), person.getId());
        assertEquals("Вася", person.getName());
        assertEquals("Почта", person.getEmail());
        assertEquals(address, person.getAddress());
        assertEquals(bankAccounts, person.getPhone());
    }

    @Test(expected = NullPointerException.class)
    public void whenNonNullableFieldIsNullThenNullPointerExceptionThrow() {
        new Person(
                null,
                "Вася",
                "Почта",
                null,
                null
        );
    }
}

