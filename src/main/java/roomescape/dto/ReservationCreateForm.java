package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationCreateForm {

    private String name;
    private LocalDate date;
    private Time time;

    public Reservation toEntity() {
        return new Reservation(null, this.name, this.date, this.time);
    }

    public ReservationCreateForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
