package roomescape.util;

import jakarta.servlet.http.Cookie;

public class CookieUtil {

    private CookieUtil() {
    }

    public static String extractToken(Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
