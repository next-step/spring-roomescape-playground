package roomescape.time.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import roomescape.time.domain.Time;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class TimeResponse {

    private Long id;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    public static TimeResponse from(Time reservationTime) {
        return new TimeResponse(
                reservationTime.getId(),
                reservationTime.getTime()
        );
    }
}
