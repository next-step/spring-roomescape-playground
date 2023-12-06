package roomescape.domain.reservation.dto.response;

import lombok.AllArgsConstructor;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.domain.time.entity.Time;

import java.time.LocalDate;

@AllArgsConstructor
public class GetReservationResponse {

    private Long id;
    private String name;
    private LocalDate localDate;
    private Time time;

    public static GetReservationResponse from(Reservation reservation) {
        return new GetReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}

