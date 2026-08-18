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

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";

        return jdbcTemplate.query(sql, (resultSet, rowNumber) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                LocalTime.parse(resultSet.getString("time"))
        ));
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, reservation.getName());
            statement.setString(2, reservation.getDate().toString());
            statement.setString(3, reservation.getTime().toString());
            return statement;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId == null) {
            throw new IllegalStateException("예약 ID를 생성하지 못했습니다.");
        }
        return reservation.withId(generatedId.longValue());
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
