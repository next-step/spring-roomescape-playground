package roomescape;


import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Controller
public class TimeController {
    private JdbcTemplate jdbcTemplate;
    public TimeController(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper
    private final RowMapper<Time> rowMapper = (resultSet,rowNum) ->
            new Time(
                    resultSet.getLong("id"),
                    resultSet.getString("time")
            );

    // Read
    @GetMapping("/times")
    @ResponseBody
    public List<Time> findAll(){
        String sql = "select id,time from time";
        return jdbcTemplate.query(sql,rowMapper);
    }

    // Create
    @PostMapping("/times")
    public ResponseEntity<Time> addTime(@RequestBody Time time){
        if(isBlank(time.getTime())){
            throw new BadRequestTimeException();
        }

        String sql = "insert into time (time) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("Failed to retrieve generated id");
        }

        Long id = key.longValue();
        return ResponseEntity.created(URI.create("/times/"+id)).build();
    }

    //Delete
    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        String sql = "delete from time where id = ?";
        int deleted = jdbcTemplate.update(sql, id);

        if (deleted == 0) {
            throw new NotFoundTimeException();
        }

        return ResponseEntity.noContent().build();
    }

    // Exception Handler
    public class NotFoundTimeException extends RuntimeException {}
    public class BadRequestTimeException extends RuntimeException {}
    @ExceptionHandler({ReservationController.BadRequestReservationException.class, ReservationController.NotFoundReservationException.class})
    public ResponseEntity<Void> handleBadRequest(RuntimeException e){
        return ResponseEntity.badRequest().build();
    }
}
