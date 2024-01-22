package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.application.ReservationService;
import roomescape.application.dto.ReadReservationDto;
import roomescape.domain.Reservation;
import roomescape.controller.dto.request.CreateReservationRequest;
import roomescape.controller.dto.response.CreateReservationResponse;
import roomescape.controller.dto.response.ReadReservationResponse;
import roomescape.controller.exception.ReservationNotFoundException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);
    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation.html";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReadReservationResponse>> readAll() {
        final List<ReadReservationDto> readReservationDtos = reservationService.readAll();
        final List<ReadReservationResponse> responses = readReservationDtos.stream()
                                                                           .map(ReadReservationResponse::from)
                                                                           .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<CreateReservationResponse> create(@RequestBody @Valid final CreateReservationRequest request) {
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

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        final Reservation target = reservations.stream()
                                               .filter(reservation -> Objects.equals(reservation.getId(), id))
                                               .findAny()
                                               .orElseThrow(() -> new ReservationNotFoundException("존재하지 않는 예약입니다."));
        reservations.remove(target);

        return ResponseEntity.noContent()
                             .build();
    }
}
