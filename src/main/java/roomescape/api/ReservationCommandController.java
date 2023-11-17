package roomescape.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.application.dto.ReservationCreateRequest;
import roomescape.application.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.SimpleReservationRepository;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/reservations")
public class ReservationCommandController {

    private final SimpleReservationRepository reservationRepository;

    public ReservationCommandController(final SimpleReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationCreateRequest request) {
        if (request.getName().isEmpty() || request.getDate().isEmpty() || request.getTime().isEmpty()) {
            throw new IllegalArgumentException("예약 정보의 인자는 비어있을 수 없습니다.");
        }
        final Reservation savedReservation = reservationRepository.save(new Reservation(request.getName(), LocalDate.parse(request.getDate()), LocalTime.parse(request.getTime())));
        return ResponseEntity.created(URI.create("/reservations/" + savedReservation.getId()))
                .body(new ReservationResponse(savedReservation.getId(), savedReservation.getName(), savedReservation.getDate(), savedReservation.getTime()));
    }
    // TODO: 2023/11/17 dto 정팩메 만들기

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable Long id) {
        reservationRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
