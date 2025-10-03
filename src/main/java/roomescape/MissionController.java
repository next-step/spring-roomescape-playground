package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MissionController {

        @GetMapping("/")
        public String index(){
                return "home";
        }
}
