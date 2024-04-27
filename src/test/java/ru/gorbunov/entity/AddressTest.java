package ru.gorbunov.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AddressTest {
    @Test
    public void whenCreateAddressWithFullConstructorThenAllFieldsAreNotNull() {
        Address address = new Address(
                123L,
                "City",
                "Street"
        );

        assertNotNull(address.getId()); // когда много ассертов, их надо запихивать в assertAll
        assertNotNull(address.getCity());
        assertNotNull(address.getStreet());

        assertEquals(Long.valueOf(123L), address.getId());
        assertEquals("City", address.getCity());
        assertEquals("Street", address.getStreet());
    }

    @Test(expected = NullPointerException.class)
    public void whenNonNullFieldIsNullThenNullPointerExceptionThrow() {
        new Address(null, null, "Street");
    }
}
