package cholog;

import cholog.dto.MemberRequest;
import cholog.entity.Member;
import cholog.entity.Person;
import cholog.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity<?> createMember(
            @RequestBody MemberRequest request
    ) {

        Member response = memberService.createMember(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/members")
    public ResponseEntity<?> readMembers() {
        List<Member> response = memberService.findAllMembers();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/members/{memberId}")
    public ResponseEntity<?> updateMember(
            @RequestBody MemberRequest request,
            @PathVariable Long memberId
    ) {
        Member updatedMember = memberService.updateMember(memberId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedMember);
    }

    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<?> deleteMember(
            @PathVariable Long memberId
    ) {
        memberService.deleteMember(memberId);

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
