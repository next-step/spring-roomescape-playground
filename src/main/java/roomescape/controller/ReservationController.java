package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.ReservationDTO;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    private List<ReservationDTO> reservations = new ArrayList<>();

    public ReservationController() {
        reservations.add(new ReservationDTO(1L, "안녕", "2024-05-13", "04:18"));
        reservations.add(new ReservationDTO(2L, "하세요", "2024-05-14", "04:18"));
        reservations.add(new ReservationDTO(3L, "감사해요", "2024-05-15", "04:18"));
    }

    @GetMapping("/reservation")
    public String Reservation(Model model) {
        model.addAttribute("reservations", reservations);
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationDTO> getReservations() {
        return reservations;
    }
}
