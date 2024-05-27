package roomescape.domain.Dto.timeDto;

import lombok.Data;
import roomescape.domain.Dto.reservationDto.ResponseDto;
import roomescape.domain.Model.Reservation;
import roomescape.domain.Model.Time;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
