package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                SELECT r.id AS reservation_id,
                       r.name,
                       r.date,
                       t.id AS time_id,
                       t.time AS time_value
                FROM reservation AS r
                INNER JOIN time AS t ON r.time_id = t.id
                ORDER BY r.id
                """;

        return jdbcTemplate.query(sql, (resultSet, rowNumber) -> Reservation.restore(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                ReservationTime.restore(
                        resultSet.getLong("time_id"),
                        LocalTime.parse(resultSet.getString("time_value"))
                )
        ));
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, reservation.getName());
            statement.setString(2, reservation.getDate().toString());
            statement.setLong(3, reservation.getTime().getId());
            return statement;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId == null) {
            throw new IllegalStateException("예약 ID를 생성하지 못했습니다.");
        }
        return Reservation.restore(
                generatedId.longValue(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
