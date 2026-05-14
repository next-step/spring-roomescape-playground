package roomescape.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import roomescape.domain.Reservation;

@Getter
@AllArgsConstructor
public class ReservationResponse {

    private Long id;
    private String name;
    private String date;
    private String time;

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
