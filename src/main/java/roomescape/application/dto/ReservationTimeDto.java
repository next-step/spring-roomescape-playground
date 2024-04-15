package roomescape.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class ReservationTimeDto {
    private final LocalTime time;

    public ReservationTime toEntity() {
        return new ReservationTime(null, time);
    }
}
