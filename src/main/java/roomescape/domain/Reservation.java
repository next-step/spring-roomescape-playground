package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private int id;
    private String name;
    private LocalDate reservationDate;
    private LocalTime reservationTime;

    public Reservation(int id, String name, LocalDate reservationDate, LocalTime reservationTime) {
        this.id = id;
        this.name = name;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public LocalTime getReservationTime() {
        return reservationTime;
    }
}
