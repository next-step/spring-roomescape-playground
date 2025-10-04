package roomescape;

import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();

    public ReservationController() {
        reservations.add(new Reservation(1L, "브라운", "2023-01-01", "10:00"));
        reservations.add(new Reservation(2L, "브라운", "2023-01-02", "11:00"));
    }

    // /reservation 요청 시 html 응답
    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    // /reservations 예약 목록 조회 요청 처리
    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
         return reservations;
        }

}
