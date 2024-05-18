package roomescape.controller;

import java.net.URI;
import java.util.List;
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
import roomescape.domain.RequestReservationDTO;
import roomescape.domain.ReservationDTO;
import roomescape.domain.Reservation;
import roomescape.domain.dao.JdbcReservationDAO;

@Controller
public class ReservationController {
    private final JdbcReservationDAO jdbcReservationDAO;

    public ReservationController(JdbcReservationDAO jdbcReservationDAO) {
        this.jdbcReservationDAO = jdbcReservationDAO;
    }

    @GetMapping(value = "/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping(value = "/reservations")
    public ResponseEntity<List<ReservationDTO>> reservations() {
        List<ReservationDTO> reservations = jdbcReservationDAO.findAll();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(reservations, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/reservations")
    public ResponseEntity<Reservation> create(@RequestBody RequestReservationDTO request) {
        Reservation reservation = request.toReservaiton();
        Reservation newReservation = jdbcReservationDAO.insert(reservation);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/reservations/" + Long.toString(newReservation.getId())));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(newReservation);
    }

    @DeleteMapping(value = "/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        jdbcReservationDAO.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
