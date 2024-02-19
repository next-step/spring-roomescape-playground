package roomescape.domain;

import roomescape.exception.Reservation.ReservationErrorMessage;
import roomescape.exception.Reservation.ReservationException;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private Time time;

    public Reservation(String name, String date, Time time) {
        this(0L, name, date, time);
    }

    public Reservation(Long id, String name, String date, Time time) {
        validateParams(name, date);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateParams(String name, String date) {
        if (name.isBlank() || date.isBlank()) {
            throw new ReservationException(ReservationErrorMessage.INVALID_DATA);
        }
    }

    public Reservation toEntity(Long id) {
        return new Reservation(id, this.name, this.date, this.time);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getTimeId() {
        return time.getId();
    }

    public Time getTime() {
        return time;
    }
}