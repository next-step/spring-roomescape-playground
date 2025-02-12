package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {

    private final Long id;
    private final String name;
    private final ReserveDateTime reserveDateTime;

    public Reservation(Long id, String name, ReserveDateTime reserveDateTime) {
        this.id = id;
        this.name = name;
        this.reserveDateTime = reserveDateTime;
    }

    public LocalDate reserveDateValue() {
        return reserveDateTime.getReservedDate();
    }

    public LocalTime reserveTimeValue() {
        return reserveDateTime.getReservedTime();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ReserveDateTime getReserveDateTime() {
        return reserveDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
