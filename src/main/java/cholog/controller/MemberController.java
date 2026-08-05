package cholog.controller;

import cholog.dto.request.MemberRequest;
import cholog.dto.response.MemberResponse;
import cholog.entity.Member;
import cholog.entity.Person;
import cholog.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> createMember(
            @RequestBody MemberRequest request
    ) {

        Member member = memberService.createMember(request);

        return toMemberResponseEntity(HttpStatus.CREATED, member);
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>> readMembers() {

        List<Member> response = memberService.findAllMembers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response.stream()
                .map(MemberResponse::toDataTransferObject)
                .toList()
        );
    }

    @PutMapping("/members/{memberId}")
    public ResponseEntity<MemberResponse> updateMember(
            @RequestBody MemberRequest request,
            @PathVariable Long memberId
    ) {

        Member updatedMember = memberService.updateMember(memberId, request);

        return toMemberResponseEntity(HttpStatus.OK, updatedMember);
    }

    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<Void> deleteMember(
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

    private ResponseEntity<MemberResponse> toMemberResponseEntity(
            HttpStatus status,
            Member member
    ) {
        return ResponseEntity.status(status).body(MemberResponse.toDataTransferObject(member));
    }
}
