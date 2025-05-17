package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public Reservation addReservation(final Reservation reservation) {
        final String query = "INSERT INTO reservation (name, date, time) VALUES (:name, :date, :time)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate().toString())
                .addValue("time", reservation.getTime().toString());

        namedParameterJdbcTemplate.update(query, params, keyHolder);

        int id = keyHolder.getKey().intValue();
        return findByID(id).orElseThrow(() -> new RuntimeException("새로 추가한 예약 조회 실패했습니다."));
    }

    public Optional<Reservation> findByID(final int id) {
        final var query = "SELECT * FROM reservation WHERE id = ?";
        try {
            Reservation result = jdbcTemplate.queryForObject(query, reservationRowMapper, id);
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Reservation> findAll() {
        final var query = "SELECT * FROM reservation";
        return jdbcTemplate.query(query, reservationRowMapper);
    }

    public void deleteReservation(final int id) {
        final var query = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    public void updateReservation(final Reservation reservation) {
        final var query = "UPDATE reservation SET name = :name, date = :date, time = :time WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate().toString())
                .addValue("time", reservation.getTime().toString())
                .addValue("id", reservation.getId());

        namedParameterJdbcTemplate.update(query, params);
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            LocalDate.parse(resultSet.getString("date"), DateTimeFormatter.ISO_DATE),
            LocalTime.parse(resultSet.getString("time"), DateTimeFormatter.ISO_TIME)
    );
}
