package roomescape.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import roomescape.domain.ReservationTime;

@Getter
@AllArgsConstructor
public class TimeResponse {

    private Long id;
    private String time;

    public static TimeResponse from(ReservationTime reservationTime) {
        return new TimeResponse(
                reservationTime.getId(),
                reservationTime.getTime()
        );
    }
}

