package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//어드민 메인 페이지 응답 구현

@Controller
public class HomeController{

    @GetMapping("/")
    public String homepage() {
        return "home.html";
    }
}