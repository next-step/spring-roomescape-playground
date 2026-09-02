package roomescape.controller;

import java.net.URI;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.exception.ReservationNotFoundException;
import roomescape.repository.ReservationRepository;

@Controller
public class ReservationController {
    private final Clock clock;

    private final ReservationRepository reservationRepository;

    private final List<Reservation> reservations = new ArrayList<>();

    private final AtomicLong idGenerator = new AtomicLong(1);

    public ReservationController(Clock clock, ReservationRepository reservationRepository) {
        this.clock = clock;
        this.reservationRepository = reservationRepository;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationRequest reservationRequest) {
        Reservation newReservation = Reservation.create(
                idGenerator.getAndIncrement(),
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time(),
                clock);
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        Reservation reservation = reservations.stream()
                .filter(existingReservation -> Objects.equals(existingReservation.getId(), reservationId))
                .findFirst()
                .orElseThrow(() -> new ReservationNotFoundException("id " + reservationId + "에 해당하는 예약을 찾을 수 없습니다."));

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }
}
