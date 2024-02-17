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
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.Reservation.ReservationErrorMessage;
import roomescape.exception.Reservation.ReservationException;
import roomescape.repository.ReservationDAO;
import roomescape.repository.TimeDAO;

@Controller
public class ReservationController {

    private final ReservationDAO reservationDAO;
    private final TimeDAO timeDAO;

    public ReservationController(ReservationDAO reservationDAO, TimeDAO timeDAO) {
        this.reservationDAO = reservationDAO;
        this.timeDAO = timeDAO;
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
        Time time = timeDAO.findById(reservationRequest.time());
        Reservation reservation = new Reservation(reservationRequest.name(), reservationRequest.date(),
                time);
        ReservationResponse reservationResponse = reservation.toEntity(reservationDAO.insert(reservation)).toResponse();
        return ResponseEntity.created(URI.create("/reservations/" + reservationResponse.id()))
                .body(reservationResponse);
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