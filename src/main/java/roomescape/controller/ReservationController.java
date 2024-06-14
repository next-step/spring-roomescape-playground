package roomescape.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import roomescape.Repository.ReservationRepository;
import roomescape.domain.Reservation;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 예약 조회
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Reservation>>getReservations() {
        List<Reservation> reservations = reservationService.getAllReservation();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservation(id));
    }

    // 예약 추가
    @PostMapping
    @ResponseBody
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation){

        Long generatedID = reservationService.createReservation(reservation);
        URI uri = URI.create("/reservations/" + generatedID);
        reservation.setId(generatedID);
        return ResponseEntity.created(uri).body(reservation);
    }


    // 예약 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        boolean isDeleted = reservationService.deleteReservation(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Reservation deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found.");
        }
    }
}