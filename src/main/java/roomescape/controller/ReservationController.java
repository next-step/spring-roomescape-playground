package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.service.ReservationValidation;
import roomescape.exception.NotFoundReservationException;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    private ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservation")
    public String gotoReservation(){
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations(){
        //TODO Repository에서 받은 Reservation List를 반환
        final var reservations = reservationRepository.findAll();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> makeReservation(@RequestBody Map<String, String> params) {
        ReservationValidation.validateReservationRequestFormat(params);
        Reservation reservation = ReservationService.makeMapToReservation((int) index.incrementAndGet(), params);
        reservations.add(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable int id) throws NotFoundReservationException {
        boolean res = reservations.removeIf(reservation -> reservation.getId() == id);
        ReservationValidation.validateReservationExists(res);
        return ResponseEntity.noContent().build();
    }

}
