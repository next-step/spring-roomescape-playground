package roomescape.controller;

import java.net.URI;
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
import roomescape.domain.NotFoundReservationException;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);


    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> read() {
        List<ReservationDto> reservationDtos = reservations.stream()
                .map(Reservation::toDTO)
                .toList();
        return ResponseEntity.ok().body(reservationDtos);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDto> create(@RequestBody ReservationDto dto) {
        Reservation reservation = new Reservation(dto.name(), dto.date(), dto.time());
        Reservation newReservation = reservation.toEntity(index.incrementAndGet(), reservation);
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.toDTO().id()))
                .body(newReservation.toDTO());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.toDTO().id(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }
}