package roomescape.service;

import org.springframework.http.ResponseEntity;
import roomescape.domain.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> getAllReservations();

    ResponseEntity<Reservation> addReservation(Reservation reservation);

    void cancelReservation(Long id);
}