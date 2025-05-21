package roomescape.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;
import roomescape.service.TimeService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final TimeService timeService;

    public ReservationController(ReservationService reservationService, TimeService timeService) {
        this.reservationService = reservationService;
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> add(@RequestBody ReservationRequest request) {
        Time time = timeService.findById(request.getTimeId());
        Reservation saved = reservationService.add(
                new Reservation(null, request.getName(), LocalDate.parse(request.getDate()), time));
        return ResponseEntity.created(URI.create("/reservations/" + saved.getId()))
                .body(new ReservationResponse(saved));
    }

    @GetMapping
    public List<ReservationResponse> findAll() {
        return reservationService.findAll().stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        reservationService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody ReservationRequest request) {
        Time time = timeService.findById(request.getTimeId());
        Reservation updated = new Reservation(id.intValue(), request.getName(), LocalDate.parse(request.getDate()), time);
        reservationService.update(updated);
    }
}
