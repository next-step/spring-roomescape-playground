package roomescape.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Member;

@Controller
public class MemberController {
    private final List<Member> members = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Member>> read() {
        return ResponseEntity.ok(members);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Member> create(@RequestBody Member member) {
        if(member.getName() == null || member.getName().isEmpty()) {
            throw new IllegalArgumentException("name 은 필수 값입니다.");
        }
        if(member.getDate() == null || member.getDate().isEmpty()) {
            throw new IllegalArgumentException("date 는 필수 값입니다.");
        }
        if(member.getTime() == null || member.getTime().isEmpty()) {
            throw new IllegalArgumentException("time 은 필수 값입니다.");
        }

     Member newMember = Member.toEntity(member, index.getAndIncrement());
     members.add(newMember);

     return ResponseEntity.created(URI.create("/reservations/" + newMember.getId())).body(newMember);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Member deletedMember = members.stream()
                .filter(member -> member.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("삭제하고자 하는 예약 id를 찾을 수 없습니다."));
        members.remove(deletedMember);

        return ResponseEntity.noContent().build();
    }
}
