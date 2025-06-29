package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.ReservationRequest;
import roomescape.model.Time;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value FROM reservation r
                INNER JOIN time t ON r.time_id = t.id""";
        return jdbcTemplate.query(sql, this::mapRowToReservation);
    }

    @Override
    public Reservation save(Reservation reservation) {
        return null;
    }

    @Override
    public Reservation save(ReservationRequest reservationRequest) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservationRequest.name());
            ps.setString(2, reservationRequest.date());
            ps.setInt(3, reservationRequest.timeId());
            return ps;
        }, keyHolder);
        int reservationId = keyHolder.getKey().intValue();
        return findById(reservationId);
    }

    public Reservation findById(int id) {
        String sql = """
                SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value
                FROM reservation r
                INNER JOIN time t ON r.time_id = t.id
                WHERE r.id = ?
                """;
        return jdbcTemplate.queryForObject(sql, this::mapRowToReservation, id);
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int resultRow = jdbcTemplate.update(sql, id);
        return resultRow > 0;
    }

    private Reservation mapRowToReservation(ResultSet rs, int rowNum) throws SQLException {
        return new Reservation(
                rs.getInt("reservation_id"),
                rs.getString("name"),
                rs.getString("date"),
                new Time(rs.getInt("time_id"), rs.getString("time_value"))
        );
    }
}
