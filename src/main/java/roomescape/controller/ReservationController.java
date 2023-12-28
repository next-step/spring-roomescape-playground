package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateForm;
import roomescape.dto.ReservationResponseForm;
import roomescape.exception.ValidationException;
import roomescape.repository.JdbcReservationRepository;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "new-reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseForm>> getReservations() {
        List<ReservationResponseForm> responseReservations = reservationService.getReservations();

        return ResponseEntity.ok().body(responseReservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseForm> createReservation(@Valid @RequestBody ReservationCreateForm form, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }

        Long newId = reservationService.save(form);
        Reservation reservation = reservationService.findById(newId);

        return ResponseEntity.created(URI.create("/reservations/" + newId))
                .body(new ReservationResponseForm(reservation));
    }

    @DeleteMapping("reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
