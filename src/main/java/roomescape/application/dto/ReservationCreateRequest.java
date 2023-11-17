package roomescape.application.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationCreateRequest {
    private final String name;
    private final String date;
    private final String time;

    public ReservationCreateRequest(final String name, final String date, final String time) {
        this.name = name;
        this.date = date;
        this.time = time;
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

    public static Reservation from(final ReservationCreateRequest request) {
        return new Reservation(
                request.getName(), LocalDate.parse(request.getDate()), LocalTime.parse(request.getTime())
        );
    }
}
