package roomescape.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import roomescape.domain.Member;
import roomescape.domain.MemberRepository;
import roomescape.util.JwtUtil;

import java.util.Arrays;
import java.util.Optional;

public class AdminInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    public AdminInterceptor(JwtUtil jwtUtil, MemberRepository memberRepository) {
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<String> token = extractToken(request.getCookies());
        if (token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        Long memberId = jwtUtil.getMemberIdFromToken(token.get());
        Optional<Member> member = memberRepository.findById(memberId);

        if (member.isEmpty() || !"ADMIN".equals(member.get().getRole())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }

    private Optional<String> extractToken(Cookie[] cookies) {
        if (cookies == null) {
            return Optional.empty();
        }
        return Arrays.stream(cookies)
                .filter(cookie -> "token".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }
}
