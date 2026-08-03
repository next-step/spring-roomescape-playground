package cholog;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@Controller
public class MemberController {

    private final AtomicLong index = new AtomicLong(1);

    @PostMapping("/members")
    public ResponseEntity<?> createMember(
            @RequestBody Member member
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Member.toEntity(index.getAndIncrement(), member));
    }

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
