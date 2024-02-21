package roomescape.service;

import roomescape.dto.ReservationDto;
import roomescape.entity.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> findAll();
    Reservation createReservation(ReservationDto reservationDto);
    void deleteReservation(Long id);
}