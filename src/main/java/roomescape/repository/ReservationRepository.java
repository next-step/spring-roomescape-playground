package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.BadRequestException;

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
                FROM reservation as r
                INNER JOIN time as t ON r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, reservationRowMapper());
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, reservation.getName());
                ps.setDate(2, java.sql.Date.valueOf(reservation.getDate()));
                ps.setTime(3, java.sql.Time.valueOf(reservation.getTime().getTime()));
                return ps;
            }, keyHolder);
        } catch (DuplicateKeyException e) {
            throw new BadRequestException("이미 예약된 날짜와 시간입니다.");
        }


        Long id = keyHolder.getKey().longValue();

        return new Reservation(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        int deletedCount = jdbcTemplate.update(sql, id);

        return deletedCount > 0;
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (rs, rowNum) -> {
            Time time = new Time(
                    rs.getLong("time_id"),
                    LocalTime.parse(rs.getString("time_value"))
            );

            return new Reservation(
                    rs.getLong("reservation_id"),
                    rs.getString("name"),
                    LocalDate.parse(rs.getString("date")),
                    time
            );
        };
    }
}