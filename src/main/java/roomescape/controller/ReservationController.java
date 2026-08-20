package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {
    private static final int NO_ROWS = 0;
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> readReservationList() {
        List<ReservationResponse> reservationResponses = reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponse> readReservation(@PathVariable Long id) {
        Reservation reservation = findReservationById(id);

        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        Reservation reservation = request.toEntity();
        Reservation savedReservation = reservationRepository.save(reservation);

        return ResponseEntity.created(URI.create("/reservations/" + savedReservation.getId()))
                .body(ReservationResponse.from(savedReservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        int deletedCount = reservationRepository.deleteById(id);

        if (deletedCount == NO_ROWS) {
            throw new NotFoundReservationException("삭제할 예약을 찾을 수 없습니다.");
        }
        return ResponseEntity.noContent().build();
    }

    private Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundReservationException("조회할 예약을 찾을 수 없습니다."));
    }
}
