package roomescape;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.ReservationConflictException;

@Controller
public class RoomescapeController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String showReservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> getReservations() {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        validateDuplicate(request);

        // Request DTO -> Entity 변환
        Reservation newReservation = Reservation.toEntity(request, index.getAndIncrement());
        reservations.add(newReservation);

        // Entity -> Response DTO 변환
        ReservationResponse response = ReservationResponse.from(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + response.getId())).body(response);
    }

    private void validateDuplicate(ReservationRequest request) {
        boolean isDuplicate = reservations.stream()
                .anyMatch(it -> it.isSameTime(request.getDate(), request.getTime()));

        if (isDuplicate) {
            throw new ReservationConflictException("중복된 예약이 존재합니다.");
        }
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);
        reservations.remove(reservation);

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
