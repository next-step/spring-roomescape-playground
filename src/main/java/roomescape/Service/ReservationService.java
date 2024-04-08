package roomescape.Service;

import org.springframework.http.ResponseEntity;
import roomescape.Domain.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> getAllReservations();

    ResponseEntity<Reservation> addReservation(Reservation reservation);

    void cancelReservation(Long id);
}