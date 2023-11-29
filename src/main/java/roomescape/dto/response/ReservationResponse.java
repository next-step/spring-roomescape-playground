package roomescape.dto.response;

import roomescape.domain.Reservation;

public class ReservationResponse {

    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    private ReservationResponse(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponse toDto(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
            reservation.getTime());
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
