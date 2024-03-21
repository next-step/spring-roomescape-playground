package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {

        Reservation reservation = new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new Time(
                        resultSet.getLong("time_id"),
                        resultSet.getString("time_value")
                )
        );
        return reservation;
    };

    @Transactional(readOnly = true)
    public List<Reservation> findAllReservations() {
        String sql = "SELECT " +
                "r.id as reservation_id, " +
                "r.name, " +
                "r.date, " +
                "t.id as time_id, " +
                "t.time as time_value " +
                "FROM reservation as r inner join time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Optional<Reservation> findReservationById(Long id) {
        String sql = "SELECT id, name, date, time_id FROM reservation WHERE id = ?";
        try {
            Reservation reservation = jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int delete(Long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Reservation saveReservation(Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time_id", reservation.getTime().getId());

        Long newId = jdbcInsert.executeAndReturnKey(parameters).longValue();

        return new Reservation(newId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

}
