package roomescape.domain.reservation;

import java.time.LocalDate;
import roomescape.domain.reservation.validator.ReserveDateAndTimeValidator;

public class ReserveDate {

    private final LocalDate value;

    public ReserveDate(LocalDate reserveDate) {
        ReserveDateAndTimeValidator.validateReserveDate(reserveDate);
        value = reserveDate;
    }

    public LocalDate getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ReserveDate{" +
                "value=" + value +
                '}';
    }
}
