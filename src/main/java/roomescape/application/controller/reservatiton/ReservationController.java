package roomescape.application.controller.reservatiton;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.controller.reservatiton.service.ReservationConverter;
import roomescape.common.error.exception.EntityNotFoundException;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReserveDate;
import roomescape.domain.reservation.ReserveTime;
import roomescape.application.dto.CreateReservationRequestDto;
import roomescape.application.dto.ReservationResponseDto;

@RestController
public class ReservationController {

    private final Map<Long, Reservation> reservations;
    private final AtomicLong index;
    private final ReservationConverter reservationConverter;

    public ReservationController(ReservationConverter reservationConverter) {
        this.reservationConverter = reservationConverter;
        this.index = new AtomicLong(1);
        reservations = new HashMap<>();
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        return ResponseEntity.ok(reservations.values().stream().map(reservationConverter::toDto).toList());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> createReservation(
            @RequestBody @Valid CreateReservationRequestDto requestDto
    ) {
        Reservation createReserve = reservationConverter.toEntity(index.getAndIncrement(), requestDto);
        reservations.put(createReserve.getId(), createReserve);
        return ResponseEntity
                .created(URI.create("/reservations/"+createReserve.getId()))
                .body(reservationConverter.toDto(createReserve));
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long reservationId
    ) {
        if (!reservations.containsKey(reservationId)) {
            throw new EntityNotFoundException();
        }
        reservations.remove(reservationId);
        return ResponseEntity.noContent().build();
    }

}
