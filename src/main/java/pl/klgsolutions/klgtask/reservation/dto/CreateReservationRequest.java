package pl.klgsolutions.klgtask.reservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pl.klgsolutions.klgtask.objectforrent.dto.ObjectForRentDto;
import pl.klgsolutions.klgtask.person.dto.PersonDto;

import java.sql.Date;

@Data
public class CreateReservationRequest {

    @NotNull
    private ObjectForRentDto object;

    @NotNull
    private PersonDto landlord;

    @NotNull
    private PersonDto renter;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private Float cost;
}
