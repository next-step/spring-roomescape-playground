package roomescape.validation;

import static roomescape.utils.ErrorMessage.*;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.Reservation;
import roomescape.utils.ErrorMessage;


public class ReservationValidation{
    public static void validate(Reservation reservation){
        isInputEmpty(reservation.getName(), EMPTY_NAME_ERROR);
        isInputEmpty(reservation.getDate(), EMPTY_DATE_ERROR);
        isInputEmpty(reservation.getTime(), EMPTY_TIME_ERROR);
    }

    private static void isInputEmpty(String input, ErrorMessage errorMessage) {
        if (!StringUtils.hasText(input)) {
            throw new NotFoundReservationException(errorMessage);
        }
    }
}
