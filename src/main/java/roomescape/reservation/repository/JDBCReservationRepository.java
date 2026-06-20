package roomescape.reservation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.model.Reservation;
import roomescape.time.model.Time;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JDBCReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        LocalDate date = resultSet.getDate("date").toLocalDate();

        Reservation reservation = new Reservation(name, date);
        reservation.setId(id);

        Long time_id = resultSet.getLong("time_id");
        LocalTime time_value = LocalTime.parse(resultSet.getString("time_value"));

        Time time = new Time(time_value);
        time.setId(time_id);

        reservation.setTime(time);

        return reservation;
    };

    @Autowired
    public JDBCReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Reservation> findReservationById(Long id) {
        String sql =
                """
                select 
                    r.id, 
                    r. name, 
                    r. date, 
                    t.id as time_id,
                    t.name as time_value
                from reservation as r inner join time as t on r.time_id = t.id 
                where id = ?
                """;

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, reservationRowMapper, id));

    }

    @Override
    public List<Reservation> findAllReservations() {
        String sql =
                """
                select 
                    r.id, 
                    r.name, 
                    r.date,
                    t.id as time_id,
                    t.time as time_value 
                from reservation as r inner join time as t on r.time_id = t.id order by r.id
                """;

        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    @Override
    public Long insertWithKeyHolder(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, date, time_id) values (?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public int delete(Reservation reservation) {
        String sql = "delete from reservation where id = ?";
        Long id = reservation.getId();
        return jdbcTemplate.update(sql, id);
    }
}