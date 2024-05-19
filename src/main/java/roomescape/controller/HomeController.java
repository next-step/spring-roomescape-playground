package roomescape.controller;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import roomescape.model.MemberDTO;
import roomescape.model.repository.MemberRepository;

import java.net.URI;
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
    public String reservationController() {
        return "reservation";
    }

    @ResponseBody
    @PostMapping("/reservations")
    public ResponseEntity<MemberDTO> reservationAddController(@Valid @RequestBody MemberDTO memberDTO) {
        MemberDTO responseDTO = memberRepository.memberAdd(memberDTO);
        HttpHeaders headers = new HttpHeaders();
        String uri = "/reservations/" + responseDTO.getId();
        headers.setLocation(URI.create(uri));
        ResponseEntity<MemberDTO> response = new ResponseEntity<>(responseDTO, headers, HttpStatus.CREATED);
        log.info("memberDTO = {}", memberDTO);
        return response;
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteController(@PathVariable int id) {
        memberRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostConstruct
    public void init() {
        memberRepository.memberAdd(new MemberDTO("브라운", "2023-01-01", "10:00"));
        memberRepository.memberAdd(new MemberDTO("브라운", "2023-01-02", "11:00"));
        memberRepository.memberAdd(new MemberDTO("브라운", "2023-01-03", "12:00"));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity handleException(NoHandlerFoundException e) {
        return ResponseEntity.badRequest().build();
    }
}
