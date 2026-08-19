package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import jakarta.validation.Valid;

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
    @ResponseBody
    public List<Reservation> reservations() {
        return reservationRepository.findAll();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(
            @Valid @RequestBody ReservationRequest request
    ) {

        Long id = index.getAndIncrement();

        Reservation reservation = new Reservation(
                id,
                request.getName(),
                request.getDate(),
                request.getTime()
        );

        reservationRepository.save(reservation);

        return ResponseEntity
                .created(URI.create("/reservations/" + id))
                .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id
    ) {

        boolean deleted = reservationRepository.deleteById(id);

        if (!deleted) {
            throw new NotFoundReservationException();
        }

        return ResponseEntity.noContent().build();
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
