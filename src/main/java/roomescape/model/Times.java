package roomescape.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.TimeDto;
import roomescape.model.errors.ReservationNotFoundException;
import roomescape.model.errors.TimeNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        this.validateTime(timeDto.time());

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

    private void validateTime(String givenTime) {
        validateFormat(givenTime);
        List<Integer> timeNumbers = Arrays.stream(givenTime.split(":"))
                .map(Integer::parseInt)
                .toList();

        validateRange(timeNumbers.get(0),timeNumbers.get(1));
    }

    private void validateFormat(String givenTime) {
        if (!Pattern.matches( "\\d{2}:\\d{2}",givenTime)) {
            throw new IllegalArgumentException("시간은 hh:mm 형식이여야 합니다.");
        }
    }

    private void validateRange(Integer hour, Integer minutes) {
        boolean isInRange = 0<=hour && hour <24 && 0<= minutes && minutes <60;
        if (!isInRange) {
            throw new IllegalArgumentException("시는 00~23, 분은 00~60 사이의 숫자로 표현해야 합니다.");
        }
    }


    public void removeById(long deletingId) {
        int deletedRowCounts = jdbcTemplate.update("DELETE FROM time WHERE id = ?", deletingId);

        if (deletedRowCounts == 0) {
            throw new TimeNotFoundException("존재하지 않는 시간대 입니다.");
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
