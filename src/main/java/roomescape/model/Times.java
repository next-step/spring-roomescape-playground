package roomescape.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.TimeDto;
import roomescape.model.errors.ReservationNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Times {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Times(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> getTimes() {
        return jdbcTemplate.query("SELECT * FROM time", this::extractTimeFromResultSet);
    }

    public Time add(TimeDto timeDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO time (time) values (?)",
                    new String[]{"id"});
            ps.setString(1, timeDto.time());

            return ps;
        }, keyHolder);

        long newId = keyHolder.getKey().longValue();
        return new Time(newId, timeDto);
    }

    public void removeById(long deletingId) throws ReservationNotFoundException {
        int deletedRowCounts = jdbcTemplate.update("DELETE FROM time WHERE id = ?", deletingId);

        if (deletedRowCounts == 0) {
            throw new ReservationNotFoundException();
        }
    }

    private Time extractTimeFromResultSet(ResultSet resultSet, int rowNum) {
        try {
            return new Time(resultSet.getLong("id"),
                    resultSet.getString("time"));
        } catch (SQLException e) {
            throw new IllegalArgumentException("time 테이블과 형식이 다릅니다.");
        }
    }
}
