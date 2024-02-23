package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.dto.Reservation;

import java.util.List;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
        return reservation;
    };

    public List<Reservation> findAll(){
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public Reservation findById(Long id){
        String sql = "SELECT id, name, date, time FROM reservation where id = ?";
        return jdbcTemplate.queryForObject(sql, actorRowMapper, id);
    }
}
