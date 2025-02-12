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

