package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.common.error.ErrorCode;
import roomescape.domain.reservation.exception.ReservationException;

public class ReservedDateTime {

    private final LocalDate reservedDate;
    private final LocalTime reservedTime;

    public ReservedDateTime(LocalDate reservedDate, LocalTime reservedTime) {
        validate(reservedDate, reservedTime);
        this.reservedDate = reservedDate;
        this.reservedTime = reservedTime;
    }

    private void validate(LocalDate reservedDate, LocalTime reservedTime) {
        if (Objects.isNull(reservedDate) || Objects.isNull(reservedTime)) {
            throw new ReservationException(ErrorCode.INVALID_RESERVE_VALUE);
        }
    }

    public LocalDate getReservedDate() {
        return reservedDate;
    }

    public LocalTime getReservedTime() {
        return reservedTime;
    }

    @Override
    public String toString() {
        return "ReservedDateTime{" +
                "reservedDate=" + reservedDate +
                ", reservedTime=" + reservedTime +
                '}';
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
        return Objects.equals(reservedDate, that.reservedDate) && Objects.equals(reservedTime,
                that.reservedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservedDate, reservedTime);
    }
}
