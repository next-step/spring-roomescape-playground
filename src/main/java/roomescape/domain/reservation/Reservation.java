package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = {"id"})
@ToString
public class Reservation {

    private final Long id;
    private final String name;
    private final ReserveDate reserveDate;
    private final ReserveTime reserveTime;

    @Builder
    public Reservation(Long id, String name, ReserveDate reserveDate, ReserveTime reserveTime) {
        this.id = id;
        this.name = name;
        this.reserveDate = reserveDate;
        this.reserveTime = reserveTime;
    }

    public LocalDate reserveDateValue() {
        return reserveDate.getValue();
    }

    public LocalTime reserveTimeValue() {
        return reserveTime.getValue();
    }

}
