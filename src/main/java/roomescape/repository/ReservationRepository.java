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
        final String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        jdbcTemplate.update(connection -> {
                    final PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
                    preparedStatement.setString(1, reservation.name());
                    preparedStatement.setString(2, reservation.date());
                    preparedStatement.setString(3, reservation.time());
                    return preparedStatement;
                }, keyHolder);
        return (Long) keyHolder.getKey();
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "select id, name, date, time from reservation",
                (resultSet, rowNum) -> Reservation.of(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")
                ));
    }

    public int delete(String id) {
        return jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
