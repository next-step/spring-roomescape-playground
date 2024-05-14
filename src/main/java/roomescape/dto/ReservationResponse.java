package roomescape.dto;

import lombok.Getter;
import lombok.Setter;
import roomescape.domain.Reservation;

@Setter
@Getter
public class ReservationResponse {

    public Long id;
    public String name;
    public String date;
    public String time;

    public static ReservationResponse from(Reservation reservation) {
        ReservationResponse dto = new ReservationResponse();
        dto.setId(reservation.getId());
        dto.setName(reservation.getName());
        dto.setDate(reservation.getDate().toString());
        dto.setTime(reservation.getTime().toString());
        return dto;
    }
}
