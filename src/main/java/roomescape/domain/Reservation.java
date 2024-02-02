package roomescape.domain;

import roomescape.exception.BadRequestException;

import static io.micrometer.common.util.StringUtils.isBlank;

public class Reservation {
        private Long time_id;
        private String name;
        private String date;
        private Time time;

    public Reservation(Long id, String name, String date, Time time) {
        this.time_id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        validateReservation(name, date, time);
    }

    public Long getId() {
        return time_id;
    }

    public void setId(Long id) {
        this.time_id = id;
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
