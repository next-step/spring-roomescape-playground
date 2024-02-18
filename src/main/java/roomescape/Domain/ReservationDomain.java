package roomescape.Domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDomain {
    private long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public ReservationDomain(long id, String name, LocalDate date, LocalTime time)
    {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public static ReservationDomain toEntity(ReservationDomain reservation, long id) {
        return new ReservationDomain(id, reservation.name, reservation.date, reservation.time);
    }
}
