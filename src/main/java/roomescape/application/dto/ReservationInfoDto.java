package roomescape.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class ReservationInfoDto {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTimeInfoDto time;

    public static ReservationInfoDto from(final Reservation reservation) {
        return new ReservationInfoDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                ReservationTimeInfoDto.from(reservation.getTime())
        );
    }
}