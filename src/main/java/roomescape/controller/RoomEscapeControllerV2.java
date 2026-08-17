package roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.exception.ReservationNotFoundException;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@Controller
public class RoomEscapeControllerV2 {

    private final ReservationService reservationService;

    public RoomEscapeControllerV2(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/")
    public String home(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return "home";
    }

    @GetMapping("/reservation")
    public String getReservation(
    ) {
        return "reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> response = reservationService.findAllReservations().stream()
                .map(ReservationResponse::toDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> postReservation(
            @RequestBody @Valid ReservationRequest request
    ) {
        Reservation reservation = reservationService.createReservation(request);

        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId()))
                .body(ReservationResponse.toDto(reservation));
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long reservationId
    ) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<Void> handleRoomEscapeException(
            ReservationNotFoundException e
    ) {
        return ResponseEntity.notFound().build();
    }
}
