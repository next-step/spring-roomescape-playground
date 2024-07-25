package roomescape.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.util.List;

@Repository
public class RoomDAO {
    private final JdbcTemplate jdbcTemplate;

    public RoomDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                        "FROM reservation as r inner join time as t on r.time_id = t.id",
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getInt("reservation_id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        new Time(resultSet.getInt("time_id"), resultSet.getString("time_value"))
                ));
    }

    public void insert(Reservation reservation) {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                reservation.getName(), reservation.getDate(), reservation.getTime().getId());
    }

    public void deletebyId(int id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }

    public int getId(Reservation reservation) {
        return jdbcTemplate.queryForObject("SELECT id FROM reservation WHERE name = ? AND date = ? AND time_id = ?",
                Integer.class, reservation.getName(), reservation.getDate(), reservation.getTime().getId());
    }

    public Reservation findById(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                            "FROM reservation as r inner join time as t on r.time_id = t.id WHERE r.id = ?",
                    (resultSet, rowNum) -> new Reservation(
                            resultSet.getInt("reservation_id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            new Time(resultSet.getInt("time_id"), resultSet.getString("time_value"))
                    ), id);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("찾는 id가 존재하지 않습니다!");
        }
}
}
