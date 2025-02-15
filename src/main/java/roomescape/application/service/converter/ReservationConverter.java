package roomescape.application.service.converter;

import org.springframework.stereotype.Component;
import roomescape.application.dto.CreateReservationRequestDto;
import roomescape.application.dto.ReservationResponseDto;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservedDateTime;

@Component
public class ReservationConverter {

    public Reservation toReservation(CreateReservationRequestDto requestDto) {
        return new Reservation(
                null,
                requestDto.name(),
                new ReservedDateTime(requestDto.date(), requestDto.time())
        );
    }

    public ReservationResponseDto toDto(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.reservedDateValue(),
                reservation.reservedTimeValue()
        );
    }
}
