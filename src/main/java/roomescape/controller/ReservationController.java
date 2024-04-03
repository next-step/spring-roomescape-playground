package roomescape.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


// 현재는 데이터베이스가 없으니, 임의의 DTO 를 컨트롤러에 생성!

@Controller
public class ReservationController {
    public static class Reservations {
        public Long id;
        public String name;
        public String date;
        public String time;

        public Reservations(Long id, String name, String date, String time) {
            this.id = id;
            this.name = name;
            this.date = date;
            this.time = time;
        }
    }
    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservations> reservations() {
        // 테스트에 맞게 3개를 생성하고 리턴

       Reservations r1 = new Reservations(1L, "브라운", "2023-01-01", "10:00");
        Reservations r2 = new Reservations(2L, "퍼플", "2023-01-02", "11:00");
        Reservations r3 = new Reservations(3L, "그린", "2023-01-03", "12:00");

        List<Reservations> response = new ArrayList<>();
        response.add(r1);
        response.add(r2);
        response.add(r3);

        return response;
    }

}
