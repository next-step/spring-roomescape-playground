package roomescape.domain;

import roomescape.exception.BadRequestException;

import static io.micrometer.common.util.StringUtils.isBlank;

public class Reservation {
        private Long ReservationId;
        private String name;
        private String date;
        private Time time;

    public Reservation(Long ReservationId, String name, String date, Time time) {
        this.ReservationId = ReservationId;
        this.name = name;
        this.date = date;
        this.time = time;
        validateReservation(name, date, time);
    }

    public Long getReservationId() {
        return this.ReservationId;
    }

    public void setId(Long ReservationId) {
        this.ReservationId = ReservationId;
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

    private void validateReservation(String name, String date, Time time) {
        if (isBlank(name) || isBlank(date) || time == null) {
            throw new BadRequestException("Name, date, and time are required for reservation creation");
        }
    }
}
