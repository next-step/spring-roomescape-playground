package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {

  @GetMapping("/reservation")
  public String reservation() {
    return "reservation";
    // GET /reservation 요청이 들어왔을 때 reservation.html 파일을 응답한다
    // 단순히 reservation 뷰를 반환한다
  }
}
