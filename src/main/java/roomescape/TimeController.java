package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
  public List<Time> getTimes() {
    String sql = "select id, time from time";
    List<Time> times = jdbcTemplate.query(sql, (result, row) -> {
      Time time = new Time(result.getLong("id"), result.getString("time"));
      return time;
    });
    return times;
  }

  @PostMapping
  public ResponseEntity<Time> postTime(@RequestBody Time time) {
    if (time.getTime().isEmpty()) return ResponseEntity.badRequest().build(); // 에러 처리
    String sql = "insert into time (time) values ?";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(con -> {
      PreparedStatement ps = con.prepareStatement(
              sql,
              new String[]{"id"}
      );
      ps.setString(1, time.getTime());
      return ps;
    }, keyHolder);
    Long id = keyHolder.getKey().longValue();
    Time added = new Time(id, time.getTime());
    return ResponseEntity.created(URI.create("/times/" + id)).body(added);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTime(@PathVariable long id) {
    String sql = "delete from time where id = ?";
    try {
      jdbcTemplate.queryForObject("select id from time where id = ?", Long.class, id);
      jdbcTemplate.update(sql, id);
    } catch (IncorrectResultSizeDataAccessException error) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.noContent().build();
  }
}
