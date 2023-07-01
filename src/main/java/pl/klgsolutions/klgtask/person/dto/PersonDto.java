package pl.klgsolutions.klgtask.person.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    private Long id;

    private String name;
}
