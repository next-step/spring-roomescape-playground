package roomescape.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.model.Reservation;
import roomescape.model.Time;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
public class JdbcReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedTemplate;

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
        Reservation reservation = new Reservation();
        reservation.setId(rs.getInt("id"));
        reservation.setName(rs.getString("name"));
        reservation.setDate(rs.getString("date"));
        Time time = new Time(rs.getInt("time_id"), rs.getString("time_value"));
        reservation.setTime(time);
        return reservation;
    };

    public JdbcReservationRepository(DataSource dataSource) {
        this.namedTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Reservation reservationAdd(Reservation reservation) {
        String sql = "insert into reservation (name, date, time_id) values (:name, :date, :time_id)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getTime().getId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedTemplate.update(sql, param, keyHolder);

        int key = keyHolder.getKey().intValue();
        reservation.setId(key);
        return reservation;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select r.id as reservation_id,r.name,r.date," +
                "r.id as time_id,t.time as time_value " +
                "from reservation as r inner join time as t on r.time_id = t.id ";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }


    @Override
    public void delete(int id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
