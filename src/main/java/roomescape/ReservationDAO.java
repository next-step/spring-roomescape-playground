package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.value.*;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
        return reservation;
    };

    public List<Reservation> findAllReservations() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public Reservation insertReservation(Reservation reservation) {
        final String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString()); // Assuming your Date class handles conversion
            ps.setString(3, reservation.getTime().toString()); // Assuming your Time class handles conversion
            return ps;
        }, keyHolder);

        Long newId = keyHolder.getKey().longValue();
        return new Reservation(newId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public void deleteReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

