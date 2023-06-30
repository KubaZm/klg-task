package pl.klgsolutions.klgtask.reservation.dto;

import org.mapstruct.Mapper;
import pl.klgsolutions.klgtask.objectforrent.dto.ObjectForRentMapper;
import pl.klgsolutions.klgtask.person.dto.PersonMapper;
import pl.klgsolutions.klgtask.reservation.Reservation;

import java.util.List;

@Mapper(uses = {PersonMapper.class, ObjectForRentMapper.class})
public interface ReservationMapper {
    ReservationDto reservationToDto(Reservation reservation);

    Reservation dtoToReservation(ReservationDto dto);

    List<ReservationDto> reservationListToDtoList(List<Reservation> reservations);

    Reservation createReservationRequestToReservation(CreateReservationRequest request);
}
