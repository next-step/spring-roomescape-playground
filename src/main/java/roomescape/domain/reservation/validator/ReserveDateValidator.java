package roomescape.domain.reservation.validator;

import java.time.LocalDate;
import java.util.Objects;
import roomescape.domain.reservation.error.ReservationException;

public class ReserveDateValidator {

    public static void validate(LocalDate reserveDate) {
        if (Objects.isNull(reserveDate)) {
            throw new ReservationException();
        }
        if (reserveDate.isBefore(LocalDate.now())) {
            throw new ReservationException();
        }
    }
}
