package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Reservation;
import roomescape.model.ReservationRequest;;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 예약 관리 페이지
    @GetMapping("/reservation")
    public String reservationPage() {
        return "new-reservation";
    }

    // 예약 조회
    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationService.getReservations();
        return ResponseEntity.ok(reservations);
    }

    // 예약 추가
    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest request) {
        Reservation newReservation = reservationService.addReservation(request);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    // 예약 삭제
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
