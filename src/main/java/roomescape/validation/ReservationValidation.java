package roomescape.validation;

import static roomescape.utils.ErrorMessage.*;

import org.springframework.util.StringUtils;
import roomescape.exception.RoomEscapeException;
import roomescape.domain.Reservation;
import roomescape.utils.ErrorMessage;


public class ReservationValidation{
    public static void validate(Reservation reservation){
        isInputEmpty(reservation.name(), EMPTY_NAME_ERROR);
        isInputEmpty(reservation.date(), EMPTY_DATE_ERROR);
        isInputEmpty(reservation.time(), EMPTY_TIME_ERROR);
    }

    private static void isInputEmpty(String input, ErrorMessage errorMessage) {
        if (!StringUtils.hasText(input)) {
            throw new RoomEscapeException(errorMessage);
        }
    }
}
