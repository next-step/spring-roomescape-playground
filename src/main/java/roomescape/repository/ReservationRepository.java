package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {

        String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.time as time_value
                FROM reservation as r inner join time as t on r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Time time = new Time(
                    rs.getLong("time_id"),
                    rs.getString("time_value")
            );
            return new Reservation(
                    rs.getLong("reservation_id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    time
            );
        });
    }

    public long insert(Reservation reservation) {
        String sql = """
                INSERT INTO reservation (name, date, time_id)
                VALUES (?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"}
            );
            ps.setString(1, reservation.name());
            ps.setString(2, reservation.date());
            ps.setLong(3, reservation.time().id());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public boolean delete(Long id) {
        String sql = """
                DELETE FROM reservation WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, id);
        return result > 0;
    }
}
