package roomescape.controller;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.NotFoundException;

@Controller
public class ReservationController {

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservationPage(Model model) {
        model.addAttribute("reservations", getSortedReservations());
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> getReservations() {
        return getSortedReservations().stream()
            .map(ReservationResponse::from)
            .toList();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ReservationResponse> createReservation(
        @RequestBody ReservationRequest request) {
        long id = index.getAndIncrement();
        Reservation reservation = new Reservation(id, request.name(), request.date(),
            request.time());
        reservations.put(id, reservation);

        return ResponseEntity
            .created(URI.create("/reservations/" + id))
            .body(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Reservation reservation = reservations.remove(id);
        if (reservation == null) {
            throw new NotFoundException(id);
        }
        return ResponseEntity.noContent().build();
    }

    private List<Reservation> getSortedReservations() {
        return reservations.values().stream()
            .sorted(Comparator.comparing(Reservation::getId))
            .toList();
    }
}
