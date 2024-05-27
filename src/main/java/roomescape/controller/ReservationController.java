package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.domain.reservation.ResponseReservation;
import roomescape.domain.reservation.dto.RequestReservationDTO;
import roomescape.domain.reservation.dto.ReservationDTO;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.dao.ReservationDAO;

@Controller
@RequestMapping(value = "/reservations")
public class ReservationController {
    private final ReservationDAO reservationDAO;

    public ReservationController(@Qualifier("jdbcReservationDAO") ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    @GetMapping
    public ResponseEntity<List<ResponseReservation>> reservations() {
        List<ReservationDTO> reservations = reservationDAO.findAll();
        List<ResponseReservation> response = reservations.stream()
                .map(ResponseReservation::toResponseReservation)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseReservation> create(@RequestBody RequestReservationDTO request) {
        Reservation reservation = request.toReservaiton();
        ResponseReservation response = reservationDAO.insert(reservation);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/reservations/" + Long.toString(response.id())));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reservationDAO.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
