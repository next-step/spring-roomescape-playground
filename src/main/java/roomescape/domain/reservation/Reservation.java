package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

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

    public LocalDate reserveDateValue() {
        return reserveDate.getValue();
    }

    public LocalTime reserveTimeValue() {
        return reserveTime.getValue();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ReserveDate getReserveDate() {
        return reserveDate;
    }

    public ReserveTime getReserveTime() {
        return reserveTime;
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

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", reserveDate=" + reserveDate +
                ", reserveTime=" + reserveTime +
                '}';
    }

}
