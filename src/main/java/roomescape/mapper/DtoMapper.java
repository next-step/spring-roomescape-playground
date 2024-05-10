package roomescape.mapper;

import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;

public class DtoMapper {

    public static ReservationResponseDto convertToDTO(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

}
