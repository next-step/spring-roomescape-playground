package roomescape.application.controller.reservatiton;

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
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReserveDate;
import roomescape.domain.reservation.ReserveTime;
import roomescape.application.dto.CreateReservationRequestDto;
import roomescape.application.dto.ReservationResponseDto;

@RestController
public class ReservationController {

    private final Map<Long, Reservation> reservations = new HashMap<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        return ResponseEntity.ok(reservations.values().stream().map(this::toDto).toList());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> createReservation(
            @RequestBody CreateReservationRequestDto requestDto
    ) {
        Reservation createReserve = toEntity(requestDto);
        reservations.put(createReserve.getId(), createReserve);
        return ResponseEntity
                .created(URI.create("/reservations/"+createReserve.getId()))
                .body(toDto(createReserve));
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long reservationId
    ) {
        reservations.remove(reservationId);
        return ResponseEntity.noContent().build();
    }

    private ReservationResponseDto toDto(Reservation reservation) {
        return  ReservationResponseDto.builder()
                .id(reservation.getId())
                .name(reservation.getName())
                .date(reservation.reserveDateValue())
                .time(reservation.reserveTimeValue())
                .build();
    }

    private Reservation toEntity(CreateReservationRequestDto requestDto) {
        ReserveDate reserveDate = new ReserveDate(requestDto.date());
        ReserveTime reserveTime = new ReserveTime(requestDto.time());
        return new Reservation(index.getAndIncrement(), requestDto.name(), reserveDate, reserveTime);
    }
}
