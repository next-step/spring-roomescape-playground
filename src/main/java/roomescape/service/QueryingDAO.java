package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import roomescape.db.ReservationEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryingDAO {

    private final JdbcTemplate jdbcTemplate;

    public final RowMapper<ReservationEntity> actorRowMapper = (resultSet, rowNum) -> {
        ReservationEntity reservationEntity = new ReservationEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
        return reservationEntity;
    };

    public List<ReservationEntity> findAllList() {

        String sql = "select * from reservation";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public int count() {

        String sql = "select count(*) from reservation";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public ReservationEntity findReservationById(Long id) {

        String sql = "select * from reservation where id=?";
        return jdbcTemplate.queryForObject(sql, actorRowMapper, id);
    }
}
