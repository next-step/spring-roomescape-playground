package roomescape.DAO;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.Domain.Time;

@Repository
public class TimeDAO {

  private final JdbcTemplate jdbcTemplate;

  public TimeDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
  public List<Time> findAllTimes() {
    String sql = "SELECT * FROM time";
    return jdbcTemplate.query(
        sql,
        timeRowMapper
    );
  }

  public Time findTimeByID(Long id) {
    String sql = "SELECT * FROM time WHERE id = ?";
    return jdbcTemplate.queryForObject(
        sql,
        timeRowMapper,
        id
    );
  }

  public List<Time> findTimesByID(Long id) {
    String sql = "SELECT * FROM time WHERE id = ?";
    return jdbcTemplate.query(
        sql,
        timeRowMapper,
        id);
  }

  public Long insertWithKeyHolder(Time time) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "INSERT INTO time (time) VALUES (?)";
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
      ps.setString(1, time.getTime());
      return ps;
    }, keyHolder);

    return keyHolder.getKey().longValue();
  }

  public int delete(Long id) {
    String sql = "delete from time where id = ?";
    return jdbcTemplate.update(sql, Long.valueOf(id));
  }

  private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> {
    return new Time(
        resultSet.getLong("id"),
        resultSet.getString("time")
    );
  };
}
