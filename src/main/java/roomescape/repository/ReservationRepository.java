package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT * FROM reservation", reservationRowMapper());
    }

    public Optional<Reservation> findWithId(Long id) {
        return jdbcTemplate.query("SELECT * FROM reservation WHERE id = ?", reservationRowMapper(), id)
                .stream().findFirst();
    }

    public Reservation insert(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());

            return ps;
        }, keyHolder);

        Number generatedKey = keyHolder.getKey();
        if (generatedKey == null) {
            throw new IllegalArgumentException("");
        }
        Long key = generatedKey.longValue();

        return Reservation.withId(reservation, key);
    }

    public void delete(Reservation reservation) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", reservation.getId());
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> Reservation.withId(
                Reservation.of(
                        resultSet.getString("name"),
                        LocalDate.parse(resultSet.getString("date")),
                        LocalTime.parse(resultSet.getString("time"))
                ),
                resultSet.getLong("id")
        );
    }
}
