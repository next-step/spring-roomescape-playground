package roomescape.domain.reservation.mapper;

import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.dto.ReservationCreateDTO;
import roomescape.domain.reservation.dto.ReservationResponseDTO;

public class ReservationMapper {

    public static Reservation toEntity(ReservationCreateDTO reservationCreateDTO) {
        if(reservationCreateDTO == null) return null;
        return new Reservation(null, reservationCreateDTO.getName(), reservationCreateDTO.getDate(), reservationCreateDTO.getTime());
    }

    public static ReservationResponseDTO toReservationResponseDTO(Reservation reservation) {
        if(reservation == null) return null;
        return new ReservationResponseDTO(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
