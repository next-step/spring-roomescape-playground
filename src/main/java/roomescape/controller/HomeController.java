package roomescape.controller;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import roomescape.model.MemberDTO;
import roomescape.model.repository.MemberRepository;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }


    @ResponseBody
    @GetMapping("/reservations")
    public List<MemberDTO> allReservationsController() {
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

    @ResponseBody
    @PostMapping("/reservations")
    public MemberDTO reservationAddController(@Valid @RequestBody MemberDTO memberDTO) {
        memberRepository.MemberAdd(memberDTO);
        log.info("memberDTO = {}", memberDTO);
        return memberDTO;
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteController(@PathVariable int id) {
        memberRepository.delete(id);
    }

    @PostConstruct
    public void init() {
        memberRepository.MemberAdd(new MemberDTO("브라운", "2023-01-01", "10:00"));
        memberRepository.MemberAdd(new MemberDTO("브라운", "2023-02-01", "10:00"));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity handleException(NoHandlerFoundException e) {
        return ResponseEntity.badRequest().build();
    }
}
