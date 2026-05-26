package roomescape.reservations.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservations.model.Reservation;
import roomescape.timeslot.model.Timeslot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcReservationRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("reservation_id"),
            rs.getString("name"),
            rs.getString("roomId"),
            rs.getObject("date", LocalDate.class),
            new Timeslot(
                    rs.getLong("time_id"),
                    rs.getObject("time_value", LocalTime.class)
            )
    );

    public JdbcReservationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Reservation> getAllReservations() {
        String query = "SELECT r.id as reservation_id, r.name, r.roomId, r.date, " +
                "t.id as time_id, t.timeslot as time_value " +
                "FROM reservation as r " +
                "INNER JOIN timeslot as t ON r.time = t.id";

        return namedParameterJdbcTemplate.query(query, Map.of(), rowMapper);
    }

    public Optional<Reservation> getReservationById(Long id) {
        String query = "SELECT r.id as reservation_id, r.name, r.roomId, r.date, " +
                "t.id as time_id, t.timeslot as time_value " +
                "FROM reservation as r " +
                "INNER JOIN timeslot as t ON r.time = t.id " +
                "WHERE r.id = :id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);

        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query, parameterSource, rowMapper));
    }

    public Long createReservation(Reservation reservation) {
        String query = "INSERT INTO reservation(name, roomId, date, time) " +
                "VALUES(:name, :roomId, :date, :timeId)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("roomId", reservation.getRoomId())
                .addValue("date", reservation.getDate())
                .addValue("timeId", reservation.getTime().getId());

        namedParameterJdbcTemplate.update(query, parameterSource, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Integer getReservationCountInTimeSlot(String roomId, LocalDate date, Long timeId) {
        String sql = "SELECT COUNT(*) FROM reservation " +
                "WHERE roomId = :roomId AND date = :date AND time = :timeId";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("roomId", roomId)
                .addValue("date", date)
                .addValue("timeId", timeId);

        return namedParameterJdbcTemplate.queryForObject(sql, parameterSource, Integer.class);
    }

    public int deleteReservationById(Long id) {
        String query = "DELETE FROM reservation " +
                "WHERE id = :id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);

        return namedParameterJdbcTemplate.update(query, parameterSource);
    }

    public boolean existsDuplicateReservationWithSameUser(LocalDate date, Long timeId, String name) {
        String query = "SELECT COUNT(*) FROM reservation " +
                "WHERE date = :date AND time = :timeId and name = :name";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("date", date)
                .addValue("timeId", timeId)
                .addValue("name", name);

        Integer affectedRowsCount = namedParameterJdbcTemplate.queryForObject(query, parameterSource, Integer.class);
        return affectedRowsCount != null && affectedRowsCount > 0;
    }
}