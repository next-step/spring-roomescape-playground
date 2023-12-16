package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationResponseForm {

    private Long id;
    private String name;
    private String date;
    private Time time;

    public ReservationResponseForm(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
    }

    public ReservationResponseForm() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
