package ru.gorbunov.entity;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Jacksonized
public class Person {
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private Address address;
    private List<Phone> phone;

    public void addPhone(Phone phone) {
        if (this.phone == null) {
            this.phone = new ArrayList<>();
        }
        this.phone.add(phone);
    }


}
