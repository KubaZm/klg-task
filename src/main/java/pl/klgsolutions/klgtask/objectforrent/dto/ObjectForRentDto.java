package pl.klgsolutions.klgtask.objectforrent.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectForRentDto {
    private Long id;

    private String name;

    private Float price;

    private Float area;

    private String description;
}
