package ru.gorbunov.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PhoneTest {
    @Test
    public void whenCreateAddressWithFullConstructorThenAllFieldsAreNotNull() {
        Phone phone = new Phone(
                1L,
                12345
        );

        assertNotNull(phone.getId());
        assertNotNull(phone.getNumber());

        assertEquals(Long.valueOf(1L), phone.getId());
        assertEquals(Integer.valueOf(12345), phone.getNumber());
    }
}
