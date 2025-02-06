package roomescape.domain.reservation;

import java.time.LocalDate;
import lombok.Getter;
import lombok.ToString;
import roomescape.domain.reservation.validator.ReserveDateAndTimeValidator;

@Getter
@ToString(of = "value")
public class ReserveDate {

    private final LocalDate value;

    public ReserveDate(LocalDate reserveDate) {
        ReserveDateAndTimeValidator.validateReserveDate(reserveDate);
        value = reserveDate;
    }
}
