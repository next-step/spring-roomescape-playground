package roomescape.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.model.Reservation;

import javax.sql.DataSource;
import java.util.List;

public class JdbcReservationRepository implements ReservationRepository {

    private final SimpleJdbcInsert jdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation reservationAdd(Reservation reservation) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(reservation);
        Number key = jdbcInsert.executeAndReturnKey(param);
        reservation.setId(key.intValue());
        return reservation;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select * from reservation";
        return jdbcTemplate.query(sql, reservationRowMapper());
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return BeanPropertyRowMapper.newInstance(Reservation.class);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
