package roomescape.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Member;
import roomescape.dto.LoginRequest;
import roomescape.dto.MemberResponse;
import roomescape.exception.NotFoundDataException;
import roomescape.repository.MemberRepository;
import roomescape.util.JwtUtil;

@Controller
public class LoginController {

    private final MemberRepository memberRepository;

    public LoginController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Void> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        Member member = memberRepository.findByEmailAndPassword(request.email(), request.password())
                                        .orElseThrow(() -> new NotFoundDataException("이메일 또는 비밀번호가 일치하지 않습니다."));

        String token = JwtUtil.generateToken(member);

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/check")
    @ResponseBody
    public ResponseEntity<MemberResponse> checkLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = extractTokenFromCookie(cookies);

        Long memberId = JwtUtil.getMemberIdFromToken(token);

        Member member = memberRepository.findById(memberId)
                                        .orElseThrow(() -> new NotFoundDataException("존재하지 않는 회원입니다."));

        MemberResponse memberResponse = new MemberResponse(member.getName(), member.getRole());
        return ResponseEntity.ok(memberResponse);
    }

    private String extractTokenFromCookie(Cookie[] cookies) {
        if (cookies == null) {
            throw new NotFoundDataException("로그인이 필요합니다.");
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                return cookie.getValue();
            }
        }

        throw new NotFoundDataException("로그인이 필요합니다.");
    }
}
