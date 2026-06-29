package roomescape.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import roomescape.domain.LoginMember;
import roomescape.exception.BadRequestException;

@Service
public class MemberService {
    private final JdbcTemplate jdbcTemplate;

    public MemberService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public LoginMember findByEmailAndPassword(String email, String password) {
        String sql = "SELECT id, name, email, role FROM member WHERE email = ? AND password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new LoginMember(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("role")
            ), email, password);
        } catch (Exception e) {
            throw new BadRequestException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
    }


    public LoginMember findById(Long id) {
        String sql = "SELECT id, name, email, role FROM member WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new LoginMember(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("role")
            ), id);
        } catch (Exception e) {
            throw new BadRequestException("존재하지 않는 회원입니다.");
        }
    }
}
