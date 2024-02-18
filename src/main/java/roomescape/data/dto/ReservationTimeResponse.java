package roomescape.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import roomescape.data.entity.ReservationTime;

@Getter
@AllArgsConstructor
public class ReservationTimeResponse {

    private long id;
    private String time;

    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getTime());
    }
}
