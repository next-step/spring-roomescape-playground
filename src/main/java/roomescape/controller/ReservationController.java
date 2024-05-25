package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.exception.NotFoundReservationException;

import javax.sql.DataSource;
import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {
    private final String NOT_FOUND_RESERVATION_MESSAGE = "삭제할 예약을 찾을 수 없습니다.";
    private final ReservationRepository reservationRepository;

    public ReservationController(DataSource dataSource) {
        this.reservationRepository = new ReservationRepository(dataSource);
    }

    @GetMapping("/reservation")
    public String newReservation() {
        return "new-reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservationList = reservationRepository.findAll();
        return ResponseEntity.ok(reservationList);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation request) {
        request.Validate();

        long id = reservationRepository.save(request);
        Reservation reservation = reservationRepository.findById(id);
        URI location = URI.create("/reservations/" + id);

        return ResponseEntity.created(location).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable long id) {
        long deletedId = reservationRepository.deleteById(id);

        if (deletedId == -1) {
            throw new NotFoundReservationException(NOT_FOUND_RESERVATION_MESSAGE);
        }

        URI location = URI.create("/reservations/" + deletedId);
        ResponseEntity.noContent().location(location).build();

        return ResponseEntity.noContent().build();
    }
}