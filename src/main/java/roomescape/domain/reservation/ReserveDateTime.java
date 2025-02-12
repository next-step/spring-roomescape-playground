package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.common.error.ErrorCode;
import roomescape.domain.reservation.exception.ReservationException;

public class ReserveDateTime {

    private final LocalDate reservedDate;
    private final LocalTime reservedTime;

    public ReserveDateTime(LocalDate reserveDate, LocalTime reserveTime) {
        validate(reserveDate, reserveTime);
        this.reservedDate = reserveDate;
        this.reservedTime = reserveTime;
    }

    public LocalDate getReservedDate() {
        return reservedDate;
    }

    public LocalTime getReservedTime() {
        return reservedTime;
    }

    private void validate(LocalDate reserveDate, LocalTime reserveTime) {
        if (Objects.isNull(reserveDate) || Objects.isNull(reserveTime)) {
            throw new ReservationException(ErrorCode.INVALID_RESERVE_VALUE);
        }
    }
}
