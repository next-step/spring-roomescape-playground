package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.exception.NotFoundReservationException;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TimeService {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public TimeService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public ResponseEntity<Time> addTime(Time time) {
        Time existingTime = getTimeByTime(time.getTime());

        if (existingTime != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/times/" + existingTime.getId());
            return new ResponseEntity<>(existingTime, headers, HttpStatus.CREATED);
        } else {
            Map<String, Object> timeParameters = new HashMap<>();
            timeParameters.put("time", time.getTime());
            Number newId = jdbcInsert.executeAndReturnKey(timeParameters);

            Time newTime = getTimeById(newId.longValue());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/times/" + newId);

            return new ResponseEntity<>(newTime, headers, HttpStatus.CREATED);
        }
    }

    public Time getTimeByTime(String time) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT id, time FROM time WHERE time = ?",
                    new Object[]{time},
                    (resultSet, rowNum) -> new Time(
                            resultSet.getLong("id"),
                            resultSet.getString("time")));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Time getTimeById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT id, time FROM time WHERE id = ?",
                    new Object[]{id},
                    (resultSet, rowNum) -> new Time(
                            resultSet.getLong("id"),
                            resultSet.getString("time")));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Time> getAllTimes() {
        return jdbcTemplate.query(
                "SELECT id, time FROM time",
                (resultSet, rowNum) -> new Time(
                        resultSet.getLong("id"),
                        resultSet.getString("time")));
    }

    public void deleteTimes(Long id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
        if (rowsAffected == 0) {
            throw new NotFoundReservationException("Time not found with id: " + id);
        }
    }
}
