package roomescape.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.application.dto.ReservationInfoDto;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class ReservationRes {

    private final Long id;

    private final String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    public static ReservationRes from(final ReservationInfoDto  reservationInfoDto) {
        return new ReservationRes(
                reservationInfoDto.getId(),
                reservationInfoDto.getName(),
                reservationInfoDto.getDate(),
                reservationInfoDto.getTime().getTime()
        );
    }
}
