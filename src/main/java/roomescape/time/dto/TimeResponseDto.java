package roomescape.time.dto;

import lombok.Data;
import roomescape.time.domain.Time;

@Data
public class TimeResponseDto {
    private Long id;
    private String time;

    public TimeResponseDto(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public static TimeResponseDto makeResponse(Time time) {
        String reservationDateTime = time.getTime();
        return new TimeResponseDto(
                time.getId(),
                reservationDateTime);
    }
}
