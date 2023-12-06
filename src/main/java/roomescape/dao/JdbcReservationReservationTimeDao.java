package roomescape.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class JdbcReservationReservationTimeDao implements ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<ReservationTime> RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> new ReservationTime(
        resultSet.getLong("id"),
        resultSet.getTime("time").toLocalTime()
    );

    public JdbcReservationReservationTimeDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("time")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(reservationTime);
        Number key = simpleJdbcInsert.executeAndReturnKey(source);
        return new ReservationTime(key.longValue(), reservationTime.getTime());
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("SELECT id, time FROM time", RESERVATION_ROW_MAPPER);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
    }

    @Override
    public boolean existsById(Long id) {
        Long result = jdbcTemplate.queryForObject("select count(*) from time where id = ?", Long.class, id);
        if (result == null) {
            return false;
        }
        return result > 0;
    }
}
