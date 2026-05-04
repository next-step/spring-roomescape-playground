package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.common.EndPointPath;
import roomescape.dto.ReservationDto;
import roomescape.model.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping(EndPointPath.RESERVATION_API_ENDPOINT_ROOT)
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllBookings() {
        return ResponseEntity.ok().body(List.copyOf(this.reservations));
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDto reservationDto) {
        int newIndex = (int) this.index.getAndIncrement();
        Reservation newReservation = new Reservation(newIndex, reservationDto.name(), reservationDto.date(), reservationDto.time());
        this.reservations.add(newReservation);

        return ResponseEntity
                .created(URI.create(EndPointPath.RESERVATION_API_ENDPOINT_ROOT + "/" + newIndex))
                .body(newReservation);
    }

    @DeleteMapping("/{deletingIndex}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer deletingIndex) {
        Reservation toDelete = this.reservations.stream()
                .filter(reservation -> deletingIndex == reservation.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Wrong Index"));

        this.reservations.remove(toDelete);
        return ResponseEntity.noContent().build();
    }
}
