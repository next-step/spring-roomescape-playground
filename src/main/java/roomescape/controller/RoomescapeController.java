package roomescape.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import roomescape.domain.Reservation;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.service.ReservationService;

@Controller
public class RoomescapeController {

    private final ReservationService reservationService;

    public RoomescapeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    private static boolean isValidReservation(Reservation reservation) {
        if (reservation == null)
            return false;
        if (reservation.getName().isEmpty() || reservation.getName().isBlank())
            return false;
        if (reservation.getDate() == null)
            return false;
        return reservation.getTime() != null;
    }

    @ExceptionHandler({NotFoundReservationException.class, InvalidReservationException.class})
    public ResponseEntity handleException(RuntimeException e) {
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/")
    public String getHome() {
        return "home";
    }

    @GetMapping("/reservation")
    public String getReservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservationList = reservationService.getAllReservations();

        return ResponseEntity.ok().body(reservationList);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        if (!isValidReservation(reservation)) {
            throw new InvalidReservationException();
        }

        Long generatedId = reservationService.addReservation(reservation);
        Reservation newReservation = Reservation.toEntity(reservation, generatedId);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> getReservations(@PathVariable Long id) {
        int deleteCount = reservationService.removeReservation(id);
        if (deleteCount == 0) {
            throw new NotFoundReservationException();
        }

        return ResponseEntity.noContent().build();
    }
}