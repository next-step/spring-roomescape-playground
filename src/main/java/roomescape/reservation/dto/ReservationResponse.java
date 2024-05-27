package roomescape.reservation.dto;

import lombok.Getter;
import lombok.Setter;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.Time;

@Setter
@Getter
public class ReservationResponse {

    private Long id;
    private String name;
    private String date;
    private Time time;

    public static ReservationResponse from(Reservation reservation) {
        ReservationResponse dto = new ReservationResponse();
        dto.setId(reservation.getId());
        dto.setName(reservation.getName());
        dto.setDate(reservation.getDate());
        dto.setTime(reservation.getTime());
        return dto;
    }
}
