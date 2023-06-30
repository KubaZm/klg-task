package pl.klgsolutions.klgtask.reservation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoNameProvidedException extends Exception {
    public NoNameProvidedException(String message) {
        super(message);
    }
}
