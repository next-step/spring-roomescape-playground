package roomescape.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import roomescape.domain.Reservation;
import roomescape.exception.BadRequestReservationException;
import roomescape.exception.NotFoundReservationException;


import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private List<Reservation> reservationList = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    // for second-stage testing purposes
//    {
//        Reservation test1 = new Reservation(index.getAndIncrement(), "name1", "2023-08-07", "11:00");
//        Reservation test2 = new Reservation(index.getAndIncrement(), "name2", "2023-08-08", "12:00");
//        Reservation test3 = new Reservation(index.getAndIncrement(), "name3", "2023-08-09", "13:00");
//
//        reservationList.add(test1);
//        reservationList.add(test2);
//        reservationList.add(test3);
//    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservationList);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation newReservation) {
        if (Reservation.isInvalid(newReservation))
            throw new BadRequestReservationException();

        Reservation reservation = Reservation.toEntity(newReservation, index.getAndIncrement());
        reservationList.add(reservation);
        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId()))
                .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Reservation reservation = reservationList.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(BadRequestReservationException::new);

        reservationList.remove(reservation);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity handleException(HttpClientErrorException.BadRequest e) {
        return ResponseEntity.badRequest().build();
    }

}
