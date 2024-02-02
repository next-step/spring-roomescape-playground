package cholog;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class MemberController {
    @GetMapping("/hello")
    public String world(@RequestParam(name ="name",required = false,defaultValue = "World") String name, Model model) {
        model.addAttribute("name",name);
        return "hello";
    }
    @GetMapping("/json")
    @ResponseBody
    public Person json() {
        return new Person("brown", 20);
    }
    private List<Member> members = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);
    @PostMapping("/members")
    public ResponseEntity<Void> create(@RequestBody Member member) {
        Member newMember = Member.toEntity(member, index.getAndIncrement());
        members.add(newMember);
        return ResponseEntity.created(URI.create("/members/" + newMember.getId())).build();
    }
    @GetMapping("/members")
    public ResponseEntity<List<Member>> read() {
        List<Member> allMembers = new ArrayList<>(members);
        return ResponseEntity.ok(allMembers);
    }
    @PutMapping("/members/{id}")
    public ResponseEntity<Void> update(@RequestBody Member newMember, @PathVariable Long id) {
        Member member = members.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        member.update(newMember);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Member member = members.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        members.remove(member);

        return ResponseEntity.noContent().build();
    }
}