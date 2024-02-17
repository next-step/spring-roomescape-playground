package roomescape.domain;

import roomescape.dto.ReservationResponse;
import roomescape.exception.Reservation.ReservationErrorMessage;
import roomescape.exception.Reservation.ReservationException;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation(String name, String date, String time) {
        this(0L, name, date, time);
    }

    public Reservation(Long id, String name, String date, String time) {
        validateParams(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateParams(String name, String date, String time) {
        if (name.isBlank() ||date.isBlank() || time.isBlank()) {
            throw new ReservationException(ReservationErrorMessage.INVALID_DATA);
        }
    }

    public ReservationResponse toResponse() {
        return new ReservationResponse(this.id, this.name, this.date, this.time);
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

    public String getTime() {
        return time;
    }
}