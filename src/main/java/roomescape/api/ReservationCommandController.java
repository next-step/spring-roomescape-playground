package roomescape.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.application.dto.ReservationCreateRequest;
import roomescape.application.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.repository.ReservationRepository;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationCommandController {

    private final ReservationRepository jdbcReservationRepository;

    public ReservationCommandController(final ReservationRepository jdbcReservationRepository) {
        this.jdbcReservationRepository = jdbcReservationRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody final ReservationCreateRequest request) {
        validateNotEmptyRequest(request);
        final Reservation savedReservation = jdbcReservationRepository.save(ReservationCreateRequest.from(request));
        return ResponseEntity.created(URI.create("/reservations/" + savedReservation.getId()))
                .body(ReservationResponse.from(savedReservation));
    }

    private void validateNotEmptyRequest(final ReservationCreateRequest request) {
        if (request.getName().isEmpty() || request.getDate().isEmpty() || request.getTime().isEmpty()) {
            throw new IllegalArgumentException("예약 정보의 인자는 비어있을 수 없습니다.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable final Long id) {
        jdbcReservationRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
