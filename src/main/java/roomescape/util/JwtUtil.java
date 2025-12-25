package roomescape.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import roomescape.domain.Member;

import javax.crypto.SecretKey;

public class JwtUtil {

    private static final String SECRET_KEY = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public static String generateToken(Member member) {
        return Jwts.builder()
                   .setSubject(member.getId().toString())
                   .claim("name", member.getName())
                   .claim("role", member.getRole())
                   .signWith(KEY)
                   .compact();
    }

    public static Long getMemberIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(KEY)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

        return Long.valueOf(claims.getSubject());
    }
}
