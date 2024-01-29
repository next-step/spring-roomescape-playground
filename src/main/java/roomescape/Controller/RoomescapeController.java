package roomescape.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomescapeController {
    @GetMapping("/")
    public String home(){
        return "home";
    }
}
