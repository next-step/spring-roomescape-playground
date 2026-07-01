package roomescape.dataLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.common.ExceptionMessage;
import roomescape.dataLayer.errors.DuplicateTimeException;
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
            throw new RuntimeException("프로그램이 존재하지 않는 시간을 접근하려 했습니다.");
        }

        return possibleTime.get(0);
    }

    private Time extractTimeFromResultSet(ResultSet resultSet, int rowNum) {
        try {
            return new Time(resultSet.getLong("id"),
                    resultSet.getString("time"));
        } catch (SQLException e) {
            throw new IllegalArgumentException(ExceptionMessage.BAD_REQUEST_REQUEST_BODY_VALID.getMessage());
        }
    }

    public Long add(String timeString) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO time (time) values (?)",
                        new String[]{"id"});
                ps.setString(1, timeString);

                return ps;
            }, keyHolder);
        } catch (Exception e) {
            throw new DuplicateTimeException(ExceptionMessage.BAD_REQUEST_REQUEST_FOR_DUPLICATE_CREATION_OF_UNIQUE_DATA.getMessage());
        }

        return keyHolder.getKey().longValue();
    }


    public void deleteTimeById(long deletingId) {
        int deletedRowCounts = jdbcTemplate.update("DELETE FROM time WHERE id = ?", deletingId);

        if (deletedRowCounts == 0) {
            throw new TimeNotFoundException(ExceptionMessage.BAD_REQUEST_REQUEST_FOR_NON_EXISTENT_DATA.getMessage());
        }
    }
}
