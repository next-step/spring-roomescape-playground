package roomescape.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.Reservation;
import roomescape.exception.ReservationInvalidException;
import roomescape.exception.ReservationNotFoundException;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();

    private final AtomicLong idGenerator = new AtomicLong(1);

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservationRequest) {
        validateName(reservationRequest);
        validateDate(reservationRequest);
        validateTime(reservationRequest);
        validateNotPast(reservationRequest);

        Reservation newReservation = Reservation.toEntity(reservationRequest, idGenerator.getAndIncrement());
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> findAllReservations() {
        return List.copyOf(reservations);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        Reservation reservation = reservations.stream()
                .filter(existingReservation -> Objects.equals(existingReservation.getId(), reservationId))
                .findFirst()
                .orElseThrow(() -> new ReservationNotFoundException("id " + reservationId + "에 해당하는 예약을 찾을 수 없습니다."));

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }

    private void validateName(Reservation reservationRequest) {
        if (reservationRequest.getName() == null || reservationRequest.getName().isBlank()) {
            throw new ReservationInvalidException("예약자 이름은 비워둘 수 없습니다.");
        }
    }

    private void validateDate(Reservation reservationRequest) {
        if (reservationRequest.getDate() == null) {
            throw new ReservationInvalidException("날짜는 비어있을 수 없습니다.");
        }
    }

    private void validateTime(Reservation reservationRequest) {
        if (reservationRequest.getTime() == null) {
            throw new ReservationInvalidException("시간은 비어있을 수 없습니다.");
        }
    }

    private void validateNotPast(Reservation reservationRequest) {
        LocalDateTime requestedDateTime = LocalDateTime.of(reservationRequest.getDate(), reservationRequest.getTime());
        if (requestedDateTime.isBefore(LocalDateTime.now())) {
            throw new ReservationInvalidException("과거 시간으로 예약할 수 없습니다");
        }
    }

}
