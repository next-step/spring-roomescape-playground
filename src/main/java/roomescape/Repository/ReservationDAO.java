package roomescape.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.Domain.Reservation;
import roomescape.Domain.Time;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class ReservationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation findReservation(Long id) {
        String sql = "select * from reservation where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, rowNum) ->
                new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")
                )
        );
    }

    public List<Reservation> findAllReservations() {
        String sql = "select id, name, date, time from reservation";
        return jdbcTemplate.query(sql, (resultSet, rowNum) ->
        {
            Reservation reservation = new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getString("time")
            );
            return reservation;
        });
    }

    public Long createReservation(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";

        if (reservation == null || !reservation.notEmpty()) {
            throw new IllegalArgumentException("누락된 사항이 있습니다. 확인해주세요.");
        }

        return insertWithKeyHolder(reservation);
    }

    public void deleteReservation(Long id) {
        String sql = "delete from reservation where id = ? ";
        int rowCheck = jdbcTemplate.update(sql, id);
        if(rowCheck == 0)
        {
            throw new NoSuchElementException("지우려는 아이디가 존재하지 않습니다." + id);
        }
    }

    private Long insertWithKeyHolder(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, reservation.getTime());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
}
