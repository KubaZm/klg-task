package pl.klgsolutions.klgtask.objectforrent.dto;

import org.mapstruct.Mapper;
import pl.klgsolutions.klgtask.objectforrent.ObjectForRent;

@Mapper
public interface ObjectForRentMapper {
    ObjectForRentDto objectToDto(ObjectForRent object);

    ObjectForRent dtoToObject(ObjectForRentDto dto);
}
