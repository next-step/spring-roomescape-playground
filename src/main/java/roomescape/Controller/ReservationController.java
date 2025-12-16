package roomescape.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.Domain.Reservation;
import roomescape.Service.ReservationService;

import java.net.URI;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Controller
public class ReservationController {

    private final ReservationService reservationService;
    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    // render
    @GetMapping("/reservation")
    public String reservationPage() {
        return "new-reservation";
    }

    // Read
    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> findAllReservations() {
        return reservationService.findAll();
    }

    // Create
    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> add_reservation(@RequestBody Reservation reservation){
        // handle exception -> any required field empty
        if(isBlank(reservation.getName()) || isBlank(reservation.getDate()) || reservation.getTime().getId()==null){
            throw new BadRequestReservationException();
        }

        Long id = reservationService.add(reservation);
        Reservation saved = Reservation.toEntity(reservation, id);

        return ResponseEntity.created(
                URI.create("/reservations/" + id)
        ).body(saved);
    }

    // Delete
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void>  cancel_reservation(@PathVariable Long id){
        int deleted = reservationService.deleteByid(id);

        // handle exception
        if (deleted == 0) {
            throw new NotFoundReservationException();
        }
        return ResponseEntity.noContent().build();
    }

    // Exception Handler
    public class NotFoundReservationException extends RuntimeException {}
    public class BadRequestReservationException extends RuntimeException {}
    @ExceptionHandler({BadRequestReservationException.class, NotFoundReservationException.class})
    public ResponseEntity<Void> handleBadRequest(RuntimeException e){
        return ResponseEntity.badRequest().build();
    }

}
