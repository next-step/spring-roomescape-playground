package roomescape.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import roomescape.Domain.Time;
import roomescape.Exception.NotFoundReservationException;

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
        this.jdbcTemplate=new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }


    public ResponseEntity<Time> addTime(Time time){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", time);
        Number newId = jdbcInsert.executeAndReturnKey(parameters);

        Time newTime = jdbcTemplate.queryForObject(
                "SELECT id, time FROM time WHERE id = ?",
                new Object[]{newId},
                (resultSet, rowNum) -> new Time(
                        resultSet.getLong("id"),
                        resultSet.getString("time")));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/times/" + newId);

        return new ResponseEntity<>(newTime, headers, HttpStatus.CREATED);
    }

    public List<Time> getAllTimes() {
        return jdbcTemplate.query(
                "SELECT id, time FROM time",
                (resultSet, rowNum) -> new Time(
                        resultSet.getLong("id"),
                        resultSet.getString("time")));
    }

    public void deleteTimes(Long id){
        int rowsAffected = jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
        if (rowsAffected == 0) {
            throw new NotFoundReservationException("Time is not found with id: " + id);
        }
    }


}
