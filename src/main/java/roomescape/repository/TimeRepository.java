package roomescape.repository;


import java.sql.PreparedStatement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Repository
@RequiredArgsConstructor
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<Time> findAll() {
        return jdbcTemplate.query(
                "select id, time from time",
                (resultSet, rowNum) -> Time.of(
                        resultSet.getLong("id"),
                        resultSet.getString("time")
                ));
    }

    public Long save(Time time) {
        final String sql = "insert into time (time) values (?)";
       jdbcTemplate.update(connection -> {
           final PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
           preparedStatement.setString(1,time.getTime());
           return preparedStatement;
       },keyHolder);
        return (Long) keyHolder.getKey();
    }
}
