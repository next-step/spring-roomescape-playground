package roomescape.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
  @GetMapping("/")
  public String home() {
    return "home";
  }
  @GetMapping("/time")
  public String time() { return "time"; }

  @GetMapping("/reservation")
  public String reservation() {
    return "new-reservation";
  }
}
