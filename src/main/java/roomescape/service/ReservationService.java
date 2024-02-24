package roomescape.service;

import roomescape.dto.ReservationDto;
import roomescape.entity.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> findAll();
    Long createReservation(ReservationDto reservationDto);
    int deleteReservation(Long id);
}