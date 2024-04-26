package ru.gorbunov.entity;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Jacksonized
public class Address {

    private Long id;
    @NonNull
    private String city;
    @NonNull
    private String street;

}