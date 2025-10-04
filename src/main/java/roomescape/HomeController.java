// 어드민 페이지 home.html 반환
package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 어드민 메인 페이지 홈 화면 반환 (요구사항)
    @GetMapping("/")
    public String home() {
    return "home";
    }
}
