package roomescape.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.request.CreateReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    public static final String HEADER_LOCATION = "Location";
    public static final String LOCATION_DEFAULT_VALUE = "/reservations/";

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping()
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody CreateReservationRequest request) {
        ReservationResponse response = reservationService.createReservation(request);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_LOCATION, LOCATION_DEFAULT_VALUE + response.id());

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(response);
    }

    @GetMapping()
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> reservationResponses = reservationService.getReservations();
        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationResponses);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
