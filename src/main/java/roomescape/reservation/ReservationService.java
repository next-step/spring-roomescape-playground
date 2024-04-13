package roomescape.reservation;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    public ReservationDTO createReservationDTO(long id, ReservationDTO reservationDTO) {
        return new ReservationDTO(id, reservationDTO.getName(), reservationDTO.getDate(), reservationDTO.getTime());
    }

    public ReservationDTO updateReservationDTO(long id, ReservationDTO reservationDTO) {
        return createReservationDTO(id, reservationDTO);
    }

}
