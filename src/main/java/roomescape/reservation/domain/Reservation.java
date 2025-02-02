package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Reservation {

    private Long id;

    private String name;

    private LocalDate date;

    private LocalTime time;

    public Long getId() {
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

    public Reservation(final Long id, final String name, final LocalDate date, final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }


    public static List<Reservation> makeDummyData() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(1L, "비토", LocalDate.of(2023, 1, 1), LocalTime.of(10, 1)));
        reservations.add(new Reservation(2L, "곰곰", LocalDate.of(2023, 1, 2), LocalTime.of(10, 2)));
        reservations.add(new Reservation(3L, "망고", LocalDate.of(2023, 1, 3), LocalTime.of(10, 3)));

        return reservations;
    }
}
