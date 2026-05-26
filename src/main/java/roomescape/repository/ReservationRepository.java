package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT * FROM reservation", reservationRowMapper());
    }

    public Optional<Reservation> findWithId(Long id) {
        return jdbcTemplate.query("SELECT * FROM reservation WHERE id = ?", reservationRowMapper(), id)
                .stream().findFirst();
    }

    public Reservation insert(Reservation reservation) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time", reservation.getTime());
        Long key = simpleJdbcInsert.executeAndReturnKey(source).longValue();
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
