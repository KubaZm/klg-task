package pl.klgsolutions.klgtask.reservation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "This object is not available in these days.")
public class ObjectNotAvailableException extends RuntimeException{

}
