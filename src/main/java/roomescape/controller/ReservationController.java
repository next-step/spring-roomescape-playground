package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationDTO;
import roomescape.exception.InvalidReservationInputException;
import roomescape.exception.ReservationNotFoundException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private List<ReservationDTO> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservation")
    public String Reservation(Model model) {
        model.addAttribute("reservations", reservations);
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationDTO> getReservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDTO> addReservation(@RequestBody ReservationDTO reservation) {
        if (reservation.name().isBlank() || reservation.date().isBlank() || reservation.time().isBlank()) {
            throw new InvalidReservationInputException("필수 입력값이 비어있습니다.", reservation.toString());
        }

        long id = index.getAndIncrement();
        ReservationDTO newReservation = new ReservationDTO(id, reservation.name(), reservation.date(), reservation.time());
        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + id)).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        Optional<ReservationDTO> reservationToRemove = reservations.stream()
                .filter(r -> r.id().equals(id))
                .findFirst();
        if (reservationToRemove.isEmpty()) {
            throw new ReservationNotFoundException(id);
        }
        reservations.remove(reservationToRemove.get());
        return ResponseEntity.noContent().build();
    }
}
