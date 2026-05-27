package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.exception.AlreadyReservedTimeException;
import roomescape.exception.NotFoundReservationException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest reservationRequest) {
        long id = reservationRepository.insert(reservationRequest);
        boolean isUnavailableTime=reservationRepository.findAll().stream()
                .anyMatch(reservation ->
                        reservation.date().equals(reservationRequest.date())
                        &&reservation.time().equals(reservationRequest.time()));
        if(isUnavailableTime){
            throw new AlreadyReservedTimeException();
        }
        Reservation reservation = new Reservation(
                id,
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );

        return ResponseEntity.created(URI.create("/reservations/" + reservation.id()))
                .body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean isRemoved = reservationRepository.delete(id);
        if (!isRemoved) {
            throw new NotFoundReservationException("존재하지 않는 예약을 지울 수 없음");
        }
        return ResponseEntity.noContent().build();
    }
}
