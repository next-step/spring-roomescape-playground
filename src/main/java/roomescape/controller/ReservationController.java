package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.Reservation;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservations") // 이 클래스의 모든 API는 /reservations 경로로 요청됩니다.
public class ReservationController {


    public ReservationController() {
        reservations.add(new Reservation( "브라운", "2025-01-01", "10:00"));
        reservations.add(new Reservation("코니", "2025-01-02", "11:00"));
    }

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    public List<Reservation> getAllReservations() {
        // ...
        return reservations;
    }
}
