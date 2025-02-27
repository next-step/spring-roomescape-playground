package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.domain.time.Time;

public class Reservation {

    private final Long id;
    private final String name;
    private final ReservedDateTime reservedDateTime;

    public Reservation(Long id, String name, ReservedDateTime reservedDateTime) {
        this.id = id;
        this.name = name;
        this.reservedDateTime = reservedDateTime;
    }

    public LocalDate reservedDateValue() {
        return reservedDateTime.getReservedDate();
    }

    public LocalTime reservedTimeValue() {
        return reservedDateTime.getTimeAsLocalTime();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ReservedDateTime getReservedDateTime() {
        return reservedDateTime;
    }

    public Long getTimeId() {
        return reservedDateTime.getTimeId();
    }

    public Time getTime() {
        return this.reservedDateTime.getTime();
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
