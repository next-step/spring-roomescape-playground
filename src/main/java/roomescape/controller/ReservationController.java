package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dto.ReservationDto;
import roomescape.model.Reservation;
import roomescape.model.Reservations;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping(ReservationController.RESERVATION_API_ENDPOINT_ROOT)
public class ReservationController {
    public final static String RESERVATION_API_ENDPOINT_ROOT = "/reservations";
    private final AtomicLong index = new AtomicLong(1);
    private final Reservations reservations;

    @Autowired
    public ReservationController(Reservations reservations) {
        this.reservations = reservations;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllBookings() {
        return ResponseEntity.ok().body(List.copyOf(this.reservations.getReservationList()));
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDto reservationDto) {
        int newIndex = (int) this.index.getAndIncrement();
        Reservation newReservation = new Reservation(newIndex, reservationDto.name(), reservationDto.date(), reservationDto.time());
        this.reservations.add(newReservation);

        return ResponseEntity
                .created(URI.create(RESERVATION_API_ENDPOINT_ROOT + "/" + newIndex))
                .body(newReservation);
    }

    @DeleteMapping("/{deletingId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer deletingId) {
        this.reservations.removeById(deletingId);
        return ResponseEntity.noContent().build();
    }
}
