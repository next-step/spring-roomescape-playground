package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.Member;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Member> memberRowMapper = (rs, rowNum) -> new Member(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("name"),
            rs.getString("role")
    );

    public Optional<Member> findByEmailAndPassword(String email, String password) {
        String sql = "SELECT id, email, password, name, role FROM member WHERE email = ? AND password = ?";
        List<Member> results = jdbcTemplate.query(sql, memberRowMapper, email, password);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Optional<Member> findById(Long id) {
        String sql = "SELECT id, email, password, name, role FROM member WHERE id = ?";
        List<Member> results = jdbcTemplate.query(sql, memberRowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Optional<Member> findByName(String name) {
        String sql = "SELECT id, email, password, name, role FROM member WHERE name = ?";
        List<Member> results = jdbcTemplate.query(sql, memberRowMapper, name);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
