package cholog;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {

    @GetMapping("/hello")
    public String getHello(
            @RequestParam(name = "name", defaultValue = "world") String name,
            Model model
    ) {
        model.addAttribute("name", name);
        return "hello";
    }

    @GetMapping("/json")
    @ResponseBody
    public ResponseEntity<Person> getJson(
            @RequestParam(name = "name", defaultValue = "brown") String name,
            @RequestParam(name = "age", defaultValue = "20") Integer age
    ) {
        return ResponseEntity.ok(new Person(name, age));
    }
}
