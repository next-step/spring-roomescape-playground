package roomescape.domain.reservation;

import java.time.LocalDate;
import lombok.Getter;
import roomescape.domain.reservation.validator.ReserveDateAndTimeValidator;

@Getter
public class ReserveDate {

    private final LocalDate value;

    public ReserveDate(LocalDate reserveDate) {
        ReserveDateAndTimeValidator.validateReserveDate(reserveDate);
        value = reserveDate;
    }

}
