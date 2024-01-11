package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController {

    private List<ReservationDto> reservations = List.of(
            new ReservationDto(1, "브라운", "2023-01-01", "10:00"),
            new ReservationDto(2, "브라운", "2023-01-02", "11:00"),
            new ReservationDto(3, "브라운", "2023-01-03", "12:00")
    );


    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> getReservations() {
        return ResponseEntity.ok()
                .body(reservations);
    }

}
