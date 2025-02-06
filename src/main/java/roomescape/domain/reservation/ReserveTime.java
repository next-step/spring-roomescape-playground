package roomescape.domain.reservation;

import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import roomescape.domain.reservation.validator.ReserveDateAndTimeValidator;

@Getter
public class ReserveTime {

    private final LocalTime value;

    public ReserveTime(LocalTime reserveTime) {
        ReserveDateAndTimeValidator.validate(reserveTime);
        value = reserveTime;
    }
}
