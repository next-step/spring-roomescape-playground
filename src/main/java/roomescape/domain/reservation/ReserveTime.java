package roomescape.domain.reservation;

import java.time.LocalTime;
import roomescape.domain.reservation.validator.ReserveDateAndTimeValidator;

public class ReserveTime {

    private final LocalTime value;

    public ReserveTime(LocalTime reserveTime) {
        ReserveDateAndTimeValidator.validate(reserveTime);
        value = reserveTime;
    }

    public LocalTime getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ReserveTime{" +
                "value=" + value +
                '}';
    }
}
