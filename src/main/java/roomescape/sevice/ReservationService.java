package roomescape.sevice;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();

    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }
}
