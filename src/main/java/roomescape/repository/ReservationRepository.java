package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) ->
            new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getObject("date", LocalDate.class),
                    resultSet.getObject("time", LocalTime.class)
            );

    public List<Reservation> findAll() {
        String sql = "select id, name, date, time from reservation";

        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Optional<Reservation> findById(Long id) {
        String sql = "select id, name, date, time from reservation where id = ?";

        return jdbcTemplate.query(sql, reservationRowMapper, id)
                .stream()
                .findFirst();
    }

    public Reservation save(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setObject(2, reservation.getDate());
            preparedStatement.setObject(3, reservation.getTime());
            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public int deleteById(Long id) {
        String sql = "delete from reservation where id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
