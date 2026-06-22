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
<<<<<<< HEAD
    private TimeResponse time;
=======
    private String time;
>>>>>>> upstream/hapdaypy

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
<<<<<<< HEAD
                TimeResponse.from(reservation.getTime())
=======
                reservation.getTime()
>>>>>>> upstream/hapdaypy
        );
    }
}
