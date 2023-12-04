package roomescape.repository;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Repository
@RequiredArgsConstructor
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    public List<Time> findAll() {
        return jdbcTemplate.query(
                "select id, time from time",
                (resultSet, rowNum) -> Time.of(
                        resultSet.getLong("id"),
                        resultSet.getString("time")
                ));
    }
}
