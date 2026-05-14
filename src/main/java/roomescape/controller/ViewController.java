package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller //웹 요청을 받는 담당자
public class ViewController {

    @GetMapping("/")
    //"/"주소로 get을 요청하면 아래 메서드 실행
    public String home() {
        return "home";
    }//화면 보여주기

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }//예약관리 페이지를 보여줌

}
