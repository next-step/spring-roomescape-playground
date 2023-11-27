package roomescape.dto.response;

import java.time.LocalDate;

import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;

public class ReservationResponse {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final String time;

    private ReservationResponse(Long id, String name, LocalDate date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public static ReservationResponse from(Long id, ReservationRequest reservation) {
        return new ReservationResponse(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
