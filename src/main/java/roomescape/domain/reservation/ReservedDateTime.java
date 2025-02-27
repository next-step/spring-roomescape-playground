package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.common.error.ErrorCode;
import roomescape.domain.reservation.exception.ReservationException;
import roomescape.domain.time.Time;

public class ReservedDateTime {

    private final LocalDate reservedDate;
    private final Time time;

    public ReservedDateTime(LocalDate reservedDate, Time time) {
        validate(reservedDate);
        this.reservedDate = reservedDate;
        this.time = time;
    }

    private void validate(LocalDate reservedDate) {
        if (Objects.isNull(reservedDate)) {
            throw new ReservationException(ErrorCode.INVALID_RESERVE_VALUE);
        }
    }

    public LocalDate getReservedDate() {
        return reservedDate;
    }

    public Time getTime() {
        return time;
    }

    public LocalTime getTimeAsLocalTime() {
        return time.getTime();
    }

    public Long getTimeId() {
        return time.getId();
    }
}
