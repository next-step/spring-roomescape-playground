package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import roomescape.model.ReservationRequest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationApiController {

    private AtomicLong index = new AtomicLong(1);
    private List<ReservationRequest> reservations = new ArrayList<>();

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationRequest>> reservations() {

        return ResponseEntity.status(HttpStatus.OK)
                .body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationRequest> createReservation(
            @Valid
            @RequestBody ReservationRequest reservationRequest
    ) {

        ReservationRequest reservationEntity = ReservationRequest.builder()
                .id(index.getAndIncrement())
                .name(reservationRequest.getName())
                .date(reservationRequest.getDate())
                .time(reservationRequest.getTime())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/reservations/" + Long.toString(reservationEntity.getId())));

        reservations.add(reservationEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(reservationEntity);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id
    ) {
        ReservationRequest reservationRequest = reservations.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        reservations.remove(reservationRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
