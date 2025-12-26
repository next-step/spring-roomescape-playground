package roomescape.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import roomescape.service.MemberService;
import roomescape.domain.Member;
import roomescape.util.CookieUtil;
import roomescape.util.JwtUtil;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private final MemberService memberService;

    public AdminInterceptor(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = CookieUtil.extractToken(request.getCookies());

        if (token == null) {
            response.sendError(401, "로그인이 필요합니다.");
            return false;
        }

        try {
            Long memberId = JwtUtil.getMemberIdFromToken(token);
            Member member = memberService.findById(memberId);

            if (!"ADMIN".equals(member.getRole())) {
                response.sendError(401, "관리자 권한이 없습니다.");
                return false;
            }

            return true;

        } catch (Exception e) {
            response.sendError(401, "유효하지 않은 토큰입니다.");
            return false;
        }
    }
}
