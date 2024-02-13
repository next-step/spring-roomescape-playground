package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.ReservationErrorMessage;
import roomescape.exception.ReservationException;
import roomescape.repository.ReservationDAO;

@Controller
public class ReservationController {

    private final ReservationDAO reservationDAO;

    public ReservationController(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> read() {
        List<ReservationResponse> reservationResponses = reservationDAO.findAll().stream()
                .map(Reservation::toResponse)
                .toList();
        return ResponseEntity.ok().body(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(reservationRequest.name(), reservationRequest.date(),
                reservationRequest.time());
        Reservation newReservation = reservation.toEntity(reservationDAO.insert(reservation));
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation.toResponse());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int deletedRows = reservationDAO.delete(id);
        if (deletedRows == 0) {
            throw new ReservationException(ReservationErrorMessage.NOT_FOUND);
        }
        return ResponseEntity.noContent().build();
    }
}