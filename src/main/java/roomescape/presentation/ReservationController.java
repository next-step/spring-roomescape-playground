package roomescape.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import roomescape.application.ReservationService;
import roomescape.domain.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);
    private final ReservationService reservationService;

    @GetMapping("")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok().body(reservationService.findAll());
    }

    @PostMapping("")
    public ResponseEntity<Reservation> create(@Valid @RequestBody Reservation reservation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new IllegalArgumentException("입력값이 잘못되었습니다");
        }
        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        Long savedId = reservationService.save(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + savedId)).body(newReservation);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
