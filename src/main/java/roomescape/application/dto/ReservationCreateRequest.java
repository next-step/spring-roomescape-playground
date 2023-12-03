package roomescape.application.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;

public class ReservationCreateRequest {
    private final String name;
    private final String date;
    private final Long timeId;

    public ReservationCreateRequest(final String name, final String date, final Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }

    public static Reservation from(final ReservationCreateRequest request) {
        return new Reservation(
                request.getName(), LocalDate.parse(request.getDate()), new Time(request.getTimeId())
        );
    }
}
