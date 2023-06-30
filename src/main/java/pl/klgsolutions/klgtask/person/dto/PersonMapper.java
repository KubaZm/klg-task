package pl.klgsolutions.klgtask.person.dto;

import org.mapstruct.Mapper;
import pl.klgsolutions.klgtask.person.Person;

@Mapper
public interface PersonMapper {
    PersonDto personToDto(Person person);

    Person dtoToPerson(PersonDto dto);
}
