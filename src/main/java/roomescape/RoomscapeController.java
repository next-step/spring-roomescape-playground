package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomscapeController {
    @GetMapping("/")
    public String home(){
        // thymeleaf 의 기본 설정이 templates 폴더에서 html 파일을 찾도록 설정되어 있습니다.
        return "home"; //home.html 파일을 반환합니다.
    }
}
