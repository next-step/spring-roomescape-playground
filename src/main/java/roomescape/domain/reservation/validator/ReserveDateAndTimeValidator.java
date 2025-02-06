package roomescape.domain.reservation.validator;

import static roomescape.common.error.ErrorCode.*;

import java.time.LocalDate;
import java.util.Objects;
import roomescape.domain.reservation.exception.ReservationException;

public class ReserveDateAndTimeValidator {

    public static void validateReserveDate(LocalDate reserveDate) {
        validate(reserveDate);
        if (reserveDate.isBefore(LocalDate.now())) {
            // TODO 에러 메시지를 콘솔에 찍을 때 조금 더 자세한 에러 내용 출력하도록 수정
            throw new ReservationException(reserveDate.toString(), INVALID_RESERVE_VALUE);
        }
    }

    public static <T> void validate(T t) {
        if (Objects.isNull(t)) {
            // TODO 입력값 에러 메시지 어떻게 처리하는게 좋을 고민
            throw new ReservationException("예약는 필수입니다.", INVALID_INPUT_VALUE);
        }
    }
}
