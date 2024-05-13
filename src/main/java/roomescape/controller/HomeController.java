package roomescape.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.model.MemberDTO;
import roomescape.model.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private List<MemberDTO> reservations = new ArrayList<>();
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservations")
    public List<MemberDTO> reservationsController() {
        List<MemberDTO> reservations = memberRepository.findAll();
        log.info("reservations = {}", reservations);
        return reservations;
    }

    @GetMapping("/reservation")
    public String reservationController(Model model) {
        List<MemberDTO> reservations = memberRepository.findAll();
        model.addAttribute("members", reservations);
        return "reservation";
    }

    @PostConstruct
    public void init() {
        memberRepository.MemberAdd(new MemberDTO(), "브라운", "2023-01-01", "10:00");
        memberRepository.MemberAdd(new MemberDTO(), "브라운", "2023-02-01", "10:00");
    }
}
