package cholog;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class MemberController {

    private final List<Member> members = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @PostMapping("/members")
    public ResponseEntity<?> createMember(
            @RequestBody Member member
    ) {
        Member createdMember = Member.toEntityWithId(index.getAndIncrement(), member);
        members.add(createdMember);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdMember);
    }

    @GetMapping("/members")
    public ResponseEntity<?> readMembers() {
        return ResponseEntity.ok(members);
    }

    @PutMapping("/members/{memberId}")
    public ResponseEntity<?> updateMember(
            @RequestBody Member member,
            @PathVariable Long memberId
    ) {
        Member foundMember = members.stream()
                .filter(element -> Objects.equals(element.getId(), memberId))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        Member updatedMember = foundMember.update(member);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedMember);
    }

    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<?> deleteMember(
            @PathVariable Long memberId
    ) {
        Member foundMember = members.stream()
                .filter(member -> Objects.equals(member.getId(), memberId))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        members.remove(foundMember);

        return ResponseEntity
                .noContent().build();
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
