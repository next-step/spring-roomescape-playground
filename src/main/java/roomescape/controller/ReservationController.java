package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<ReservationResponse> reservations() {
        List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream()
                    .map(ReservationResponse::from)
                    .toList();
    }
}
