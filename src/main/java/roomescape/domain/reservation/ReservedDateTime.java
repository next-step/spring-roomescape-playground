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
        validate(reservedDate, time);
        this.reservedDate = reservedDate;
        this.time = time;
    }

    private void validate(LocalDate reservedDate, Time time) {
        if (Objects.isNull(reservedDate) || Objects.isNull(time)) {
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
        return time.getAvailableTime();
    }

    public Long getTimeId() {
        return time.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReservedDateTime that = (ReservedDateTime) o;
        return Objects.equals(reservedDate, that.reservedDate) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservedDate, time);
    }
}
