package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.domain.RequestReservation;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;

import java.net.URI;
import java.util.List;

@RequestMapping("/reservations")
@Controller
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository repository) {
        this.reservationRepository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> checkReservations() {
        reservationRepository.addReservation(new Reservation("ㅇㅇ","2014","1231"));

        List<Reservation> reservations = reservationRepository.findAll();

        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody RequestReservation request){

        Reservation newReservation = request.toReservation();
        reservationRepository.addReservation(newReservation);

        URI location = URI.create("/reservations/" + newReservation.getId());

        return ResponseEntity.created(location).body(newReservation);
    }
}
