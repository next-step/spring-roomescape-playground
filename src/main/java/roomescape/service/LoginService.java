package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Member;
import roomescape.domain.MemberRepository;
import roomescape.util.JwtUtil;

@Service
public class LoginService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public LoginService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public String login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 아이디 또는 비밀번호가 일치하지 않습니다."));

        member.checkPassword(password);

        return jwtUtil.createToken(member);
    }
}
