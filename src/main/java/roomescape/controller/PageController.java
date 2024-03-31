package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {

    // localhost:8080 요청 시 메인 페이지가 응답할 수 있도록 구현 (templates/home.html 연결)
    @GetMapping("/")
    public String home() {
        return "home";
    }

    // localhost:8080/reservation 요청 시 예약 페이지가 응답할 수 있도록 구현 (templates/reservation.html 연결)
    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }
}