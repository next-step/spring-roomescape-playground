package roomescape.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import roomescape.domain.Time;

@Getter
@AllArgsConstructor
public class TimeResponse {

    private Long id;
    private String time;

    public static TimeResponse from(Time reservationTime) {
        return new TimeResponse(
                reservationTime.getId(),
                reservationTime.getTime()
        );
    }
}
