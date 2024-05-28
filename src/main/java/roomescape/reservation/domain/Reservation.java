package roomescape.reservation.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.time.domain.Time;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private long id;
    private String name;
    private LocalDate date;
    private Time time;

    public Reservation() {
    }

    public Reservation(long id, String name, LocalDate date, Time time) {
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

    public Time getTime() {
        return time;
    }

    public void setId(long id) {
        this.id = id;
    }

}
