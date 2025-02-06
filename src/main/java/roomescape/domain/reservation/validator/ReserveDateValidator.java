package roomescape.domain.reservation.validator;

import static roomescape.common.error.ErrorCode.*;

import java.time.LocalDate;
import java.util.Objects;
import roomescape.domain.reservation.exception.ReservationException;

public class ReserveDateValidator {

    public static void validate(LocalDate reserveDate) {
        if (Objects.isNull(reserveDate)) {
            throw new ReservationException("예약 날짜는 필수입니다.", INVALID_INPUT_VALUE);
        }
        if (reserveDate.isBefore(LocalDate.now())) {
            throw new ReservationException("오늘보다 이전 날은 예약할 수 없습니다.", INVALID_INPUT_VALUE);
        }
    }
}
