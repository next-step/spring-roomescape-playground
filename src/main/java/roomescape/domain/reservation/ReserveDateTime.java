package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.common.error.ErrorCode;
import roomescape.domain.reservation.exception.ReservationException;

public class ReserveDateTime {

    private final LocalDate reserveDate;
    private final LocalTime reserveTime;

    public ReserveDateTime(LocalDate reserveDate, LocalTime reserveTime) {
        validate(reserveDate, reserveTime);
        this.reserveDate = reserveDate;
        this.reserveTime = reserveTime;
    }

    public LocalDate getReserveDate() {
        return reserveDate;
    }

    public LocalTime getReserveTime() {
        return reserveTime;
    }

    private void validate(LocalDate reserveDate, LocalTime reserveTime) {
        if (Objects.isNull(reserveDate) || Objects.isNull(reserveTime)) {
            throw new ReservationException(ErrorCode.INVALID_RESERVE_VALUE);
        }
    }
}
/** 예약을 생성하는 비즈니스 로직으로 서비스 레이어에서 처리하는게 바람직하겠다.
 *         if (LocalDateTime.of(reserveDate, reserveTime).isBefore(LocalDateTime.now())) {
 *             throw new ReservationException(ErrorCode.INVALID_RESERVE_VALUE);
 *         }
 */
