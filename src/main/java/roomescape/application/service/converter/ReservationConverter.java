package roomescape.application.service.converter;

import org.springframework.stereotype.Component;
import roomescape.application.dto.CreateReservationRequestDto;
import roomescape.application.dto.ReservationResponseDto;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReserveDate;
import roomescape.domain.reservation.ReserveTime;

@Component
public class ReservationConverter {

    public Reservation toReservation(CreateReservationRequestDto requestDto) {
        return Reservation.builder()
                .id(null)
                .name(requestDto.name())
                .reserveDate(new ReserveDate(requestDto.date()))
                .reserveTime(new ReserveTime(requestDto.time()))
                .build();
    }

    public ReservationResponseDto toDto(Reservation reservation) {
        return  ReservationResponseDto.builder()
                .id(reservation.getId())
                .name(reservation.getName())
                .date(reservation.reserveDateValue())
                .time(reservation.reserveTimeValue())
                .build();
    }
}
