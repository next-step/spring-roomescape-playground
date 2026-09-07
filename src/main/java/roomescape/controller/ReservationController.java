package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.exception.NotFoundReservationException;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationDao;
import roomescape.dto.ReservationRequestDto;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReservationController {

    private final ReservationDao reservationDao;

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> readAll() {
        return ResponseEntity.ok().body(reservationDao.findAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@Valid @RequestBody ReservationRequestDto requestDto) {
        Reservation reservation = new Reservation(
                null,
                requestDto.getName(),
                requestDto.getDate(),
                requestDto.getTime()
        );

        Long id = reservationDao.insert(reservation);

        Reservation newReservation = Reservation.toEntity(reservation, id);
        return ResponseEntity.created(URI.create("/reservations/" + id)).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int deleteNumber = reservationDao.deleteReservation(id);

        if(deleteNumber == 0) {
            throw new NotFoundReservationException("Reservation not found: id=" + id);
        }

        return ResponseEntity.noContent().build();
    }
}
