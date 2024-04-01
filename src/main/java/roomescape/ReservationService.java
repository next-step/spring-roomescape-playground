package roomescape;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReservationService {
    List<Reservation> getAllReservations();

    ResponseEntity<Reservation> addReservation(Reservation reservation);

    void cancelReservation(Long id);
}