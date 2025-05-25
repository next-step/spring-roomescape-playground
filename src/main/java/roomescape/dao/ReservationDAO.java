package roomescape.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Repository
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation add(final Reservation reservation) {
        Map<String, Object> parameters = Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate().toString(),
                "time_id", reservation.getTime().getId()
        );

        Number generatedId = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return findByID(generatedId.intValue())
                .orElseThrow(() -> new IllegalStateException("예약 저장 후 조회에 실패했습니다."));
    }

    public Optional<Reservation> findByID(final int id) {
        final var query = """
                    SELECT r.id AS reservation_id, r.name, r.date,
                           t.id AS time_id, t.time AS time_value
                    FROM reservation r
                    INNER JOIN time t ON r.time_id = t.id
                    WHERE r.id = ?
                """;
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, reservationRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Reservation> findAll() {
        final var query = """
                    SELECT r.id AS reservation_id, r.name, r.date,
                           t.id AS time_id, t.time AS time_value
                    FROM reservation r
                    INNER JOIN time t ON r.time_id = t.id
                    ORDER BY r.id
                """;
        return jdbcTemplate.query(query, reservationRowMapper);
    }

    public boolean findByTimeId(Long timeId) {
        final var query = "SELECT COUNT(*) FROM reservation WHERE time_id = ?";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, timeId);
        return count != null && count > 0;
    }

    public void delete(final int id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }

    public void update(final Reservation reservation) {
        final var query = "UPDATE reservation SET name = :name, date = :date, time_id = :timeId WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate().toString())
                .addValue("timeId", reservation.getTime().getId())
                .addValue("id", reservation.getId());
        namedParameterJdbcTemplate.update(query, params);
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getInt("reservation_id"),
            resultSet.getString("name"),
            LocalDate.parse(resultSet.getString("date"), DateTimeFormatter.ISO_DATE),
            new Time(resultSet.getLong("time_id"), resultSet.getString("time_value"))
    );
}
