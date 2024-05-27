package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.NotFoundReservationException;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final String NOT_FOUND_RESERVATION_MESSAGE = "삭제할 예약을 찾을 수 없습니다.";
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> reservationList = reservationService.findAll();
        return ResponseEntity.ok(reservationList);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        long id = reservationService.save(request);
        ReservationResponse reservation = reservationService.findById(id);
        URI location = URI.create("/reservations/" + id);

        return ResponseEntity.created(location).body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable long id) {
        long deletedId = reservationService.deleteById(id);

        if (deletedId == -1) {
            throw new NotFoundReservationException(NOT_FOUND_RESERVATION_MESSAGE);
        }

        return ResponseEntity.noContent().build();
    }
}