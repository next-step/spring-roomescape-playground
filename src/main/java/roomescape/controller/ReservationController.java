package roomescape.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.controller.exception.NotFoundException;
import roomescape.domain.Reservation;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping(value = "/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping(value = "/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        if(reservations.isEmpty())
            throw new NotFoundException("예약된 정보가 없습니다.");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(reservations, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        System.out.println("\n\ntest : " +reservation + "\n\n");
        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/reservations/" + Long.toString(newReservation.getId())));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(newReservation);
    }

    @DeleteMapping(value = "/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }
}
