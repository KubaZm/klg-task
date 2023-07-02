package pl.klgsolutions.klgtask.reservation.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pl.klgsolutions.klgtask.objectforrent.dto.ObjectForRentDto;
import pl.klgsolutions.klgtask.person.dto.PersonDto;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @AssertTrue(message = "endDate must be later than startDate")
    private boolean isEndDateAfterStartDate() {
        return endDate.after(startDate);
    }
}
