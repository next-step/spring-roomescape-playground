package roomescape.dto;

import roomescape.domain.Time;

public class ReservationAddResponse {
    private final String name;
    private final String date;
    private final Time time;
    private long id;

    public ReservationAddResponse(long id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationAddResponse toDto(long id, String name, String date, Time time) {
        return new ReservationAddResponse(id, name, date, time);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
}
