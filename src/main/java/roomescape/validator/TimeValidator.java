package roomescape.validator;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import roomescape.domain.Time;
import roomescape.exception.InvalidReservationException;

@Component
public class TimeValidator {

    private final JdbcTemplate jdbcTemplate;

    public TimeValidator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void validateDuplicate(Time time) {
        String sql = "SELECT count(1) FROM time WHERE time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, java.sql.Time.valueOf(time.getTime()));

        if (count != null && count > 0) {
            throw new InvalidReservationException("이미 존재하는 예약 시간입니다.");
        }
    }
}
