package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.ReservationConflictException;
import roomescape.service.RoomescapeService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final RoomescapeService roomescapeService;

    public ReservationController(RoomescapeService roomescapeService) {
        this.roomescapeService = roomescapeService;
    }

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return roomescapeService.findAllReservations();
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        ReservationResponse response = roomescapeService.saveReservation(request);
        return ResponseEntity.created(URI.create("/reservations/" + response.getId())).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        roomescapeService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<String> handleException(NotFoundReservationException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<String> handleBadRequest(InvalidReservationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ReservationConflictException.class)
    public ResponseEntity<String> handleReservationConflictException(ReservationConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
