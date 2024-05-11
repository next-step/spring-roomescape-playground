package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.domain.dto.ReservationRequestDto;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationQueryingDAO {

    private JdbcTemplate jdbcTemplate;
    private TimeQueryingDAO timeQueryingDAO;

    public ReservationQueryingDAO(JdbcTemplate jdbcTemplate, TimeQueryingDAO timeQueryingDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.timeQueryingDAO = timeQueryingDAO;
    }

    public List<Reservation> findAllReservation() {

        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                     "FROM reservation as r inner join time as t on r.time_id = t.id";
        return jdbcTemplate.query(
                sql, (resultSet, rowNum) -> {

                    Time time = new Time(
                            resultSet.getLong("time_id"),
                            resultSet.getString("time_value")
                    );

                    Reservation reservation = new Reservation(
                            resultSet.getLong("reservation_id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            time
                    );
                    return reservation;
                }
        );
    }

    public Reservation insertReservation(ReservationRequestDto.Create request) {
        Time time = timeQueryingDAO.findTimeById(request.getTimeId());
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, request.getName());
            ps.setString(2, request.getDate());
            ps.setLong(3, time.getId());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return new Reservation(id, request.getName(), request.getDate(), time);
    }

    public int delete(Long id) {
        String sql = "delete from reservation where id =?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
