package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.Time;

@Repository
public class TimeRepository {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  TimeRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private final RowMapper<Time> rowMapper = (rs, rowNum) ->
      Time.create(rs.getLong("id"), rs.getString("time"));


  public List<Time> findAll() {
    String selectSql = "select * from time";
    return jdbcTemplate.query(selectSql, rowMapper);
  }

  public Time create(String time) {
    KeyHolder keyHolder = new GeneratedKeyHolder();

    String insertSql = "INSERT INTO time (time) VALUES (?)";
    jdbcTemplate.update((connection) -> {
      PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
      ps.setString(1, time);
      return ps;
    }, keyHolder);

    Long generatedId = keyHolder.getKey().longValue();
    return Time.create(generatedId, time);
  }

  public void deleteById(Long id) {
    String deleteSql = "DELETE FROM time WHERE id = ?";

    int rowsAffected = jdbcTemplate.update(deleteSql, id);
    if (rowsAffected == 0) {
      throw new NoSuchElementException();
    }
  }
}
