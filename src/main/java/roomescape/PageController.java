package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class PageController {
    @GetMapping("/")
    public String GetHome (){
        return "home";
    }

    @GetMapping("/reservation")
    public String GetReservation (Model model){
        return "reservation";
    }
}
