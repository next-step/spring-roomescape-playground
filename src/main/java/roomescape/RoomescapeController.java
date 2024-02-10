package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class RoomescapeController {
    @GetMapping("/")
    public String home(Model model) {
        return "home"; // templates/home.html을 찾아 렌더링
    }
}
