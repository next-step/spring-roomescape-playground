package roomescape.controller;

import java.net.URI;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.domain.reservation.ReservationService;
import roomescape.domain.reservation.dto.ResponseReservationDTO;
import roomescape.domain.reservation.dto.RequestReservationDTO;
import roomescape.domain.reservation.dto.ReservationDTO;

@Controller
@RequestMapping(value = "/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseReservationDTO>> reservations() {
        List<ReservationDTO> reservations = reservationService.findAll();
        List<ResponseReservationDTO> response = reservations.stream()
                .map(ResponseReservationDTO::toResponseReservation)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseReservationDTO> create(@RequestBody RequestReservationDTO request) {
        ResponseReservationDTO response = reservationService.create(request);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/reservations/" + Long.toString(response.id())));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
