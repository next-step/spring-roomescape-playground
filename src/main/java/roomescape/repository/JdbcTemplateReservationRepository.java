package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcTemplateReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReservationRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Reservation> findAllReservation() {
        String sql =
                """
                SELECT
                  r.id as reservation_id,
                  r.name,
                  r.date,
                  t.id as time_id,
                  t.time as time_value,
                FROM reservation as r inner join time as t on r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation insertReservation(Reservation reservation) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        return new Reservation(keyHolder.getKey().longValue(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public boolean deleteReservation(Long id) {
        String sql = "delete from reservation where id = ?";

        // 삭제에 성공하면 1반환 실패하면 0 반환
        return jdbcTemplate.update(sql, Long.valueOf(id)) == 1;
    }

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
        Reservation reservation = new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                rs.getString("date"),
                new Time(rs.getLong("time_id"),
                        rs.getString("time_value"))
        );
        return reservation;
    };
}
