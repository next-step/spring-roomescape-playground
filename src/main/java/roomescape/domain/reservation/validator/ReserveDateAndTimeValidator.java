package roomescape.domain.reservation.validator;


import java.time.LocalDate;
import java.util.Objects;
import roomescape.common.error.ErrorCode;
import roomescape.domain.reservation.exception.ReservationException;

public class ReserveDateAndTimeValidator {

    public static void validateReserveDate(LocalDate reserveDate) {
        validate(reserveDate);
        if (reserveDate.isBefore(LocalDate.now())) {
            throw new ReservationException(ErrorCode.INVALID_RESERVE_VALUE);
        }
    }

    public static <T> void validate(T t) {
        if (Objects.isNull(t)) {
            throw new ReservationException(ErrorCode.INVALID_RESERVE_VALUE);
        }
    }
}
