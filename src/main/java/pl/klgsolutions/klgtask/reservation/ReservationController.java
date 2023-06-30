package pl.klgsolutions.klgtask.reservation;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.klgsolutions.klgtask.reservation.dto.CreateReservationRequest;
import pl.klgsolutions.klgtask.reservation.dto.ReservationDto;
import pl.klgsolutions.klgtask.reservation.exceptions.NoNameProvidedException;
import pl.klgsolutions.klgtask.reservation.exceptions.ObjectDoesntExistException;
import pl.klgsolutions.klgtask.reservation.exceptions.PersonDoesntExistException;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/renter/{name}")
    public ResponseEntity<List<ReservationDto>> getReservationsByRenter(@PathVariable String name) throws PersonDoesntExistException {
        return new ResponseEntity<>(reservationService.getReservationsByRenter(name), HttpStatus.OK);
    }

    @GetMapping("/object/{name}")
    public ResponseEntity<List<ReservationDto>> getReservationsByObject(@PathVariable String name) throws ObjectDoesntExistException {
        return new ResponseEntity<>(reservationService.getReservationsByObject(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@Valid @RequestBody CreateReservationRequest request) throws NoNameProvidedException, PersonDoesntExistException, ObjectDoesntExistException {
        return new ResponseEntity<>(reservationService.createReservation(request), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ReservationDto> updateReservation(@Valid @RequestBody ReservationDto reservationDto) throws PersonDoesntExistException, ObjectDoesntExistException, NoNameProvidedException {
        return new ResponseEntity<>(reservationService.updateReservation(reservationDto), HttpStatus.OK);
    }
}
