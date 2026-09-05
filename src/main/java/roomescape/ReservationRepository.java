package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepository {

    private static final RowMapper<Reservation> ROW_MAPPER =
            (rs, rowNum) -> new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getString("time")
            );

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";

        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public Optional<Reservation> findById(Long id) {
        String sql =
                "SELECT id, name, date, time FROM reservation WHERE id = ?";

        return jdbcTemplate.query(sql, ROW_MAPPER, id)
                .stream()
                .findFirst();
    }

    public Reservation save(ReservationRequest request) {
        String sql =
                "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps =
                    connection.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    );

            ps.setString(1, request.getName());
            ps.setString(2, request.getDate());
            ps.setString(3, request.getTime());

            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return new Reservation(
                id,
                request.getName(),
                request.getDate(),
                request.getTime()
        );
    }

    public int update(Long id, ReservationRequest request) {
        String sql =
                "UPDATE reservation SET name = ?, date = ?, time = ? WHERE id = ?";

        return jdbcTemplate.update(
                sql,
                request.getName(),
                request.getDate(),
                request.getTime(),
                id
        );
    }

    public int delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
