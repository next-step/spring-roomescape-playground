package roomescape;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {

        List<Reservation> reservations = reservationRepository.getReservations();

        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> postReservation(
            @RequestBody ReservationRequest reservationRequest
    ) {

        // 예외처리
        if (reservationRequest.getName() == null
                || reservationRequest.getName().trim().isBlank()
                || reservationRequest.getDate() == null
                || reservationRequest.getTime() == null) {
            throw new IllegalArgumentException();
        }

        Reservation reservation = reservationRepository.postReservation(
                reservationRequest.getName(),
                reservationRequest.getDate(),
                reservationRequest.getTime()
        );

        return ResponseEntity.created(
                        URI.create("/reservations/" + reservation.getId()))
                .body(reservation);
    }

    @GetMapping("/reservations/{id}")
    @ResponseBody
    public Reservation getReservation(@PathVariable long id) {

        return reservationRepository.getReservation(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {

        int deletedCount = reservationRepository.deleteReservation(id);

        if (deletedCount == 0) {
            throw new NoSuchElementException();
        }

        return ResponseEntity.noContent().build();
    }
}
