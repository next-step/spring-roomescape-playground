package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insertWhithKeyHolder(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into reservation (name, date, time) values (?,?,?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,
                    new String[]{"id"}
            );
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, reservation.getTime());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return id;
    }

    public List<Reservation> findAllReservation() {
        String sql = "select id,name,date,time from reservation";
        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    Reservation reservation = Reservation.newReservationFromDb(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getString("time")

                    );
                    return reservation;
                });
    }


    public int delete(Long id) {
        return jdbcTemplate.update("delete from reservation where id = ?",
                Long.valueOf(id));
    }
}
