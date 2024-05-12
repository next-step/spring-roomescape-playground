package roomescape.reservation;


import org.springframework.stereotype.Component;
import roomescape.reservation.domain.Reservation;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservationDTOMapper {

    public List<ReservationDTO> convertToDtoList(List<Reservation> reservations) {
        return reservations.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Reservation toEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation(reservationDTO.getId(), reservationDTO.getName(), reservationDTO.getDate(), reservationDTO.getTime());
        return reservation;
    }

    public ReservationDTO toDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
        return reservationDTO;
    }

}
