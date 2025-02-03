package roomescape.controller;

import org.springframework.ui.Model;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();

    public ReservationController() {
        reservations.add(new Reservation(1, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)));
        reservations.add(new Reservation(2, "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0)));
        reservations.add(new Reservation(3, "샐리", LocalDate.of(2023, 1, 3), LocalTime.of(12, 0)));
    }


    // `@ResponseBody`를 사용하여 예약 목록 JSON 형태로 반환
    @GetMapping
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservations;
    }


}
