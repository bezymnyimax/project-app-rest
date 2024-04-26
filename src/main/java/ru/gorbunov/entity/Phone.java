package ru.gorbunov.entity;

import lombok.*;
import lombok.extern.jackson.Jacksonized;


@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class Phone {

    private Long id;
    @NonNull
    private Integer number;

}
