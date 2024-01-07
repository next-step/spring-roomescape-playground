package roomescape;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class RoomescapeController {
    // index 페이지를 /resources/templates/hello.html로 설정
    @GetMapping("/")
    public String index() {
        return "hello";
    }
}
