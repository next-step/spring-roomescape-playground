package roomescape.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.Domain.Time;

import java.sql.PreparedStatement;
import java.util.List;
@Repository
public class TimeRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;
  public String findTimeById(Long id) {
    String sql = "select time from time where id = ?";
    String time = jdbcTemplate.queryForObject(sql, String.class, id);

    return time;
  }

  public List<Time> getAllTimes() {
    String sql = "select id, time from time";
    List<Time> times = jdbcTemplate.query(sql, (result, row) -> {
      Time time = new Time(result.getLong("id"), result.getString("time"));
      return time;
    });
    return times;
  }

  public Time makeNewTime(Time time) {
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
    Time newTime = new Time(id, time.getTime());
    return newTime;
  }

  public void deleteTimeById(Long id) {
    String sql = "delete from time where id = ?";
    try {
      jdbcTemplate.queryForObject("select id from time where id = ?", Long.class, id);
      jdbcTemplate.update(sql, id);
    } catch (IncorrectResultSizeDataAccessException error) {
      throw error;
    }
  }
}
