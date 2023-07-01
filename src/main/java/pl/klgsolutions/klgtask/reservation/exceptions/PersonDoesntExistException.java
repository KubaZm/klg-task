package pl.klgsolutions.klgtask.reservation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Person with provided name doesn't exist.")
public class PersonDoesntExistException extends Exception {
}
