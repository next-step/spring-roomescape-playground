package roomescape.DTO;

import roomescape.Domain.Reservation;
import roomescape.Domain.Time;

public class ReservationResponseDTO {
    private Long id;
    private String name;
    private String date;
    private Time time;

    private ReservationResponseDTO(Long id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponseDTO from(Reservation reservation) {
        return new ReservationResponseDTO(reservation.getId(), reservation.getName(), reservation.getDate(),
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

    public Time getTime() {
        return time;
    }
}