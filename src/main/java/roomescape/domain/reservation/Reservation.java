package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public class Reservation {

    private final Long id;
    private final String name;
    private final ReserveDate reserveDate;
    private final ReserveTime reserveTime;

    public Reservation(Long id, String name, ReserveDate reserveDate, ReserveTime reserveTime) {
        this.id = id;
        this.name = name;
        this.reserveDate = reserveDate;
        this.reserveTime = reserveTime;
    }

    public LocalDate getReserveDateValue() {
        return reserveDate.getValue();
    }

    public LocalTime getReserveTimeValue() {
        return reserveTime.getValue();
    }

}
