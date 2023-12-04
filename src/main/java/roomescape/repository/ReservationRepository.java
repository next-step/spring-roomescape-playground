package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final KeyHolder keyHolder = new GeneratedKeyHolder();
    //private Long index = 1L;

    public Long save(Reservation reservation) {
        final String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        jdbcTemplate.update(connection -> {
                    final PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
                    preparedStatement.setString(1, reservation.name());
                    preparedStatement.setString(2, reservation.date());
                    preparedStatement.setString(3, String.valueOf(reservation.time()));
                    return preparedStatement;
                }, keyHolder);
        return (Long) keyHolder.getKey();
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                    "SELECT"
                            + " r.id as reservation_id, "
                            + " r.name, "
                            + " r.date, "
                            + " t.id as time_id, "
                            + " t.time as time_value "
                        + " FROM reservation as r inner join time as t on r.time_id = t.id",
                (resultSet, rowNum) -> Reservation.of(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getLong("time_id")
                ));
    }

    public int delete(String id) {
        return jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
