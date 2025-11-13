package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationCreateResponse;
import roomescape.model.Reservation;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /*
     * View Mapping
     */

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    /*
     * API Mapping
     */

    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservationService.getReservations();
    }

    @ResponseBody
    @PostMapping("/reservations")
    public ResponseEntity<ReservationCreateResponse> createReservation(@RequestBody @Valid ReservationCreateRequest request) {
        Reservation reservation = reservationService.createReservation(request);

        URI location = URI.create("/reservations/" + reservation.id());
        return ResponseEntity.created(location).body(ReservationCreateResponse.from(reservation));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable int id) {
        reservationService.deleteReservation(id);
    }
}
