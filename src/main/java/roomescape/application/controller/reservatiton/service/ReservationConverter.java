package roomescape.application.controller.reservatiton.service;

import org.springframework.stereotype.Component;
import roomescape.application.dto.CreateReservationRequestDto;
import roomescape.application.dto.ReservationResponseDto;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReserveDate;
import roomescape.domain.reservation.ReserveTime;

@Component
public class ReservationConverter {

    public Reservation toEntity(Long id, CreateReservationRequestDto requestDto) {
        ReserveDate reserveDate = new ReserveDate(requestDto.date());
        ReserveTime reserveTime = new ReserveTime(requestDto.time());
        return new Reservation(id, requestDto.name(), reserveDate, reserveTime);
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
