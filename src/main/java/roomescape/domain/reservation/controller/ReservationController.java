package roomescape.domain.reservation.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.reservation.dao.ReservationRepository;
import roomescape.domain.reservation.model.Reservation;

@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationRepository reservationRepository;

    @GetMapping("/reservation")
    public String getReservationPage() {
        return "reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }
}
