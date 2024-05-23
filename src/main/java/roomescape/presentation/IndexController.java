package roomescape.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }
}
