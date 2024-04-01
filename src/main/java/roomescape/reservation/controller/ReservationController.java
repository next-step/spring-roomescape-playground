package roomescape.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.reservation.domain.Reservation;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    public ReservationController(){
        // 예약 생성 로직이 없으므로, ReservationController가 생성될 때, 임의로 더미데이터를 넣었습니다.
        reservations.add(new Reservation(1L, "유승훈1", "2023-01-01", "10:00"));
        reservations.add(new Reservation(2L, "유승훈2", "2023-01-02", "11:00"));
        reservations.add(new Reservation(3L, "유승훈3", "2023-01-03", "12:00"));
    }
    @GetMapping("/reservation")
    public String reservation(){
        return "reservation"; //reservation.html 파일을 반환합니다.
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> reservationList() {
        return reservations; // 더미데이터(Reservation의 리스트)를 반환합니다.
    }
}
