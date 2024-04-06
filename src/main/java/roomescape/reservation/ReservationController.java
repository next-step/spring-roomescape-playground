package roomescape.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    ReservationService reservationService;
    List<ReservationDTO> reservationDTOs = new ArrayList<>();

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String world() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDTO>> read() {
        return ResponseEntity.ok().body(reservationDTOs);
    }


}
