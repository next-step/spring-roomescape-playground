package roomescape.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import roomescape.Reservation;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/reservation")
public class ReservationController {

    List<Reservation> reservation = new ArrayList<>();// 전역변수.

    @GetMapping("/reservationData")

    public List<Reservation> getReservations() {
        // 예약 목록을 담을 리스트 생성

        reservation.add(new Reservation("1", "브라운", "2023-01-01", "10:00"));
        reservation.add(new Reservation("2", "브라운", "2023-01-02", "11:00"));

        // 예약 목록 반환
        return reservation;
    }




   @GetMapping(value= "/reservation",produces = "text/html")
    public String world(Model model) {
        model.addAttribute("reservationData", reservation);
        return "reservationpage";//html 페이지를 못찾아요
    }
}

