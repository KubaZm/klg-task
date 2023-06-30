package pl.klgsolutions.klgtask.reservation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Please provide id of reservation which should be updated.")
public class NoIdProvidedException extends Exception {
}
