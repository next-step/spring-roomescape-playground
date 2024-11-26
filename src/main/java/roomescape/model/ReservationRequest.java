package roomescape.model;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.exception.InvalidReservationException;

public class ReservationRequest {
    private String name;
    private String date;
    private String time;
    private Long timeId;

    public ReservationRequest() {
    }

    public ReservationRequest(String name, String date, String timeId) {
        this.name = name;
        this.date = date;
        this.time = timeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getTimeId() {
        return timeId;
    }

    public void validateAndSetTimeId(JdbcTemplate jdbcTemplate) {
        if (name == null || name.isEmpty() || date == null || date.isEmpty() || time == null || time.isEmpty()) {
            throw new InvalidReservationException("필수 입력값이 누락되었습니다.");
        }

        try {
            timeId = Long.parseLong(time);
        } catch (NumberFormatException e) {
            throw new InvalidReservationException("유효하지 않은 timeId입니다: " + time);
        }

        String timeQuery = "SELECT time FROM time WHERE id = ?";
        try {
            // timeId로 유효성 검사
            String resolvedTime = jdbcTemplate.queryForObject(timeQuery, String.class, timeId);
            if (resolvedTime == null) {
                throw new InvalidReservationException("유효하지 않은 timeId입니다: " + timeId);
            }
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidReservationException("유효하지 않은 시간입니다: " + timeId);
        }
    }
}
