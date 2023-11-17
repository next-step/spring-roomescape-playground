package roomescape.domain.reservation.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.reservation.dao.ReservationRepository;
import roomescape.domain.reservation.dto.CreateReservationRequest;
import roomescape.domain.reservation.model.Reservation;

@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationRepository reservationRepository;

    @GetMapping("/reservation")
    public String getReservationPage() {
        return "reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    @ResponseBody
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody CreateReservationRequest requestDto) {
        Reservation reservation = requestDto.toEntity();
        reservationRepository.save(reservation);
        return ResponseEntity.status(CREATED)
                .location(URI.create("/reservations/" + reservation.getId()))
                .body(reservation);
    }
}
