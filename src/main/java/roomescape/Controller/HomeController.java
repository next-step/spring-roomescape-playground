package roomescape.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model1) {
        model1.addAttribute("name", "jaewon");
        return "home";
    }
}


