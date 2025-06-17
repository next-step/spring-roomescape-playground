package roomescape.reservation.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.exception.InvalidReservationRequestException;
import roomescape.reservation.exception.ReservationNotFoundException;
import roomescape.reservation.model.Reservation;
import roomescape.reservation.request.ReservationRequestDto;

@RestController
@RequestMapping("/reservations")
public class ReservationRestController {

    private final AtomicLong index = new AtomicLong(1);

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Reservation>> get() {
        // @ResponseBody가 자동으로 적용됨
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> post(
        @RequestBody ReservationRequestDto request
    ) {
        validateRequest(request);
        Reservation reservation = new Reservation(index.getAndIncrement(), request.getName(),
            request.getDate(), request.getTime());
        reservations.add(reservation);

        // Location 헤더에 생성된 리소스의 URI 추가
        return ResponseEntity
            .created(URI.create(
                "/reservations/" + reservation.getId()))  // created를 통해 201 상태 코드 + Location 설정
            .body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable Long id
    ) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId().equals(id));
        if (!removed) {
            throw new ReservationNotFoundException("Reservation not found with id: " + id);
        }
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    private void validateRequest(ReservationRequestDto request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new InvalidReservationRequestException("Name is required");
        }
        if (request.getDate() == null) {
            throw new InvalidReservationRequestException("Date is required");
        }
        if (request.getTime() == null) {
            throw new InvalidReservationRequestException("Time is required");
        }
    }
}
