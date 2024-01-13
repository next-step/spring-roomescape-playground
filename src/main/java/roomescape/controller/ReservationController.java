package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.CreateReservationResponse;
import roomescape.dto.ReadReservationResponse;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation.html";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReadReservationResponse>> readAll() {
        final List<ReadReservationResponse> responses = reservations.stream()
                                                                    .map(ReadReservationResponse::from)
                                                                    .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<CreateReservationResponse> create(@RequestBody final CreateReservationRequest request) {
        final Reservation newReservation = new Reservation(
                index.incrementAndGet(),
                request.getName(),
                request.getDate(),
                request.getTime()
        );
        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId()))
                             .body(CreateReservationResponse.from(newReservation));
    }
}
