package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.List;

@RestController
@RequestMapping(value = "/times")
public class TimeController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<Time> getAllTime() {
        List<Time> timeList = jdbcTemplate.query(
                "select id, time from time",
                (resultSet, rowNum) -> {
                    Time time = new Time(
                            resultSet.getLong("id"),
                            resultSet.getString("time")
                    );
                    return time;
                });

        return timeList;
    }

    @PostMapping
    public ResponseEntity<Time> createTime(@RequestBody Time time) {
        if(time.getTime().isEmpty() ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into time (time) values (?)",
                    new String[]{"id"});
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        Time newTime = new Time(id, time.getTime());

        return ResponseEntity.created(URI.create("/times/" + id)).body(newTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        String sql = "DELETE FROM time WHERE id = ?";

        Integer count = jdbcTemplate.queryForObject("SELECT count(*) from time where id = ?", Integer.class, id);
        if (count == 0) return ResponseEntity.badRequest().build();

        jdbcTemplate.update(sql, id);

        return ResponseEntity.noContent().build();
    }

}
