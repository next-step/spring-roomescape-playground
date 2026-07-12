package roomescape.model.time;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import roomescape.exception.NotFoundTimeException;

import java.sql.PreparedStatement;
import java.util.List;

@Service
public class TimeService {
    private final JdbcTemplate jdbcTemplate;
    private final TimeDAO timeDAO;

    public TimeService(JdbcTemplate jdbcTemplate, TimeDAO timeDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.timeDAO = timeDAO;
    }

    public List<Time> getAllTimes() {
        return timeDAO.findAll();
    }

    public Time addTime(TimeDTO timeDTO) {
        String timeValue = timeDTO.getTime();
        Time time = timeDTO.toTime();

        String sql = "INSERT INTO time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            time.setId(generatedId.longValue());
        }
        return time;
    }

    public void deleteTime(Long id) {
        int rowAffected = timeDAO.deleteById(id);
        if (rowAffected == 0) {
            throw new NotFoundTimeException("삭제하려는 시간이 존재하지 않습니다.");
        }
    }

    public Time findTimeById(Long timeId) {
        String sql = "SELECT * FROM time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Time(
                rs.getLong("id"),
                rs.getString("time")
        ), timeId);
    }
}
