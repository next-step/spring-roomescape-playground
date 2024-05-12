package roomescape.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.model.MemberDTO;
import roomescape.model.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class HomeController {

    private List<MemberDTO> reservations = new ArrayList<>();

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservations")
    public List<MemberDTO> reservationController() {
        MemberRepository memberRepository = new MemberRepository();
        memberRepository.MemberAdd(new MemberDTO(), "브라운", "2023-01-01", "10:00");
        memberRepository.MemberAdd(new MemberDTO(), "브라운", "2023-02-01", "10:00");
        List<MemberDTO> reservations = memberRepository.findAll();
        log.info("reservations = {}", reservations);
        return reservations;
    }

}
