package roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.reservation.ReservationJdbcDAO;
import roomescape.entity.Reservation;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getReservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation createReservation(@RequestBody Reservation reservation, HttpServletResponse response) {

        Reservation newReservation = reservationService.createReservation(reservation);
        response.setHeader("Location", "/reservations/" + newReservation.getId());
        return newReservation;
    }

    @GetMapping("/{id}")
    public Reservation getReservationDetail(@PathVariable long id) {
        return reservationService.getReservationById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable long id) {
        reservationService.deleteReservationById(id);
    }


}
