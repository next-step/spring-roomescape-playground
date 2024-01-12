package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class PageController {
    @GetMapping("/")
    public String goHome (){
        return "home";
    }

    @GetMapping("/reservation")
    public String reservationPage (){
        return "new-reservation";
    }

    @GetMapping("/time")
    public String timePage () { return "time";}
}
