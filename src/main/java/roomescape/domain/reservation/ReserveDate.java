package roomescape.domain.reservation;

import java.time.LocalDate;
import lombok.Getter;
import roomescape.domain.reservation.validator.ReserveDateValidator;

@Getter
public class ReserveDate {

    private final LocalDate value;

    public ReserveDate(LocalDate reserveDate) {
        ReserveDateValidator.validate(reserveDate);
        value = reserveDate;
    }

}
