package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

@Repository
public class H2ReservationRepository implements ReservationRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public H2ReservationRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select * from reservations";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getObject("date", LocalDate.class),
                        rs.getObject("time", LocalTime.class)
                )
        );
    }

    @Override
    public Reservation save(final Reservation reservation) {
        String sql = "insert into reservations (name, date, time) "
                + "values (:name, :date, :time)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(reservation);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);
        final long keyId = keyHolder.getKey().longValue();
        return new Reservation(keyId, reservation.getName(), reservation.getDate(),
                reservation.getTime());
    }

    @Override
    public void deleteById(final Long id) {

    }
}
