package roomescape.dataLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dataLayer.errors.TimeNotFoundException;
import roomescape.model.Time;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> getTimes() {
        return jdbcTemplate.query("SELECT * FROM time", this::extractTimeFromResultSet);
    }

    public Time getTimeById(Long id) {
        List<Time> possibleTime = jdbcTemplate.query("SELECT * FROM time WHERE id = ?", this::extractTimeFromResultSet, id);

        if (possibleTime.isEmpty()) {
            throw new TimeNotFoundException("존재하지 않는 시간입니다");
        }

        return possibleTime.get(0);
    }

    private Time extractTimeFromResultSet(ResultSet resultSet, int rowNum) {
        try {
            return new Time(resultSet.getLong("id"),
                    resultSet.getString("time"));
        } catch (SQLException e) {
            throw new IllegalArgumentException("time_id 테이블과 형식이 다릅니다.");
        }
    }

    public Long add(String timeString) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO time (time) values (?)",
                    new String[]{"id"});
            ps.setString(1, timeString);

            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }


    public void deleteTimeById(long deletingId) {
        int deletedRowCounts = jdbcTemplate.update("DELETE FROM time WHERE id = ?", deletingId);

        if (deletedRowCounts == 0) {
            throw new TimeNotFoundException("존재하지 않는 시간대 입니다.");
        }
    }
}
