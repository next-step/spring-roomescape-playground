package roomescape.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationCreateForm;
import roomescape.dto.ReservationResponseForm;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }
    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationResponseForm>> getReservations() {
        List<ReservationResponseForm> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ReservationResponseForm> createReservation(@Valid @RequestBody ReservationCreateForm form, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new ReservationResponseForm());
        }

        ReservationResponseForm newReservation = reservationService.createReservation(form);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);

        return ResponseEntity.noContent().build();
    }
}
