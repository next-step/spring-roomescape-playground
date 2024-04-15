package roomescape.presentation.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.application.dto.ReservationTimeInfoDto;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class ReservationTimeRes {
    private final Long id;
    private final LocalTime time;

    public static ReservationTimeRes from(final ReservationTimeInfoDto reservationTimeInfoDto){
        return new ReservationTimeRes(
                reservationTimeInfoDto.getId(),
                reservationTimeInfoDto.getTime()
        );
    }
}
