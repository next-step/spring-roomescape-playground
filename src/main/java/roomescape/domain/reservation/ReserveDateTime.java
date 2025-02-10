package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.common.error.ErrorCode;
import roomescape.domain.reservation.exception.ReservationException;

public class ReserveDateTime {

    private final LocalDateTime reserveDateTime;

    public ReserveDateTime(LocalDate reserveDate, LocalTime reserveTime) {
        validate(reserveDate, reserveTime);
        this.reserveDateTime = LocalDateTime.of(reserveDate, reserveTime);
    }

    public LocalDate getReserveDate() {
        return reserveDateTime.toLocalDate();
    }

    public LocalTime getReserveTime() {
        return reserveDateTime.toLocalTime();
    }

    private void validate(LocalDate reserveDate, LocalTime reserveTime) {
        if (Objects.isNull(reserveDate) || Objects.isNull(reserveTime)) {
            throw new ReservationException(ErrorCode.INVALID_RESERVE_VALUE);
        }
        if (LocalDateTime.of(reserveDate, reserveTime).isBefore(LocalDateTime.now())) {
            throw new ReservationException(ErrorCode.INVALID_RESERVE_VALUE);
        }
    }
}
