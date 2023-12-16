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
import roomescape.repository.JdbcReservationRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private final JdbcReservationRepository reservationRepository;

    public ReservationController(JdbcReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "new-reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationResponseForm>> getReservations() {
        List<Reservation> all = reservationRepository.findAll();
        ResponseEntity<List<ReservationResponseForm>> body = ResponseEntity.ok().body(reservationRepository
                .findAll()
                .stream()
                .map(ReservationResponseForm::new)
                .toList());
        return body;
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ReservationResponseForm> createReservation(@Valid @RequestBody ReservationCreateForm form, BindingResult result) {
        Reservation newReservation = form.toEntity();
        Long newId = reservationRepository.save(newReservation);
        Reservation reservation = reservationRepository.findById(newId);

        return ResponseEntity.created(URI.create("/reservations/" + newId))
                .body(new ReservationResponseForm(reservation));
    }

    @DeleteMapping("reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
