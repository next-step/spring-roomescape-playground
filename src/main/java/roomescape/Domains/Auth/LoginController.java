package roomescape.Domains.Auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  final private String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
  private final UserRepository userRepository;

  public LoginController(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping("/login")
  public final ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
    String password = credentials.get("password");
    String email = credentials.get("email");
    String name = "어드민";

    this.userRepository.save(new User(name, email, password));

    Claims claims = Jwts.claims().setSubject(name);

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + 1000L * 60 * 60); // 토큰 만료 시간: 1시간 후

    String accessToken = Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS256, secretKey).compact();

    return ResponseEntity.ok().header("Set-Cookie", "token=" + accessToken + ";").build();
  }

  @GetMapping("/login/check")
  public final ResponseEntity<Map<String, String>> loginCheck(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    String token = getToken(cookies);
    String memberName = String.valueOf(Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody().getSubject());
    return ResponseEntity.ok().body(Map.of("name", memberName));
  }

  private static String getToken(final Cookie[] cookies) {
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("token")) {
        return cookie.getValue();
      }
    }
    return "";
  }

}
