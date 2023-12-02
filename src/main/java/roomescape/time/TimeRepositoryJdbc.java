package roomescape.time;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class TimeRepositoryJdbc {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Time> findAll() {
    String sql = "SELECT * FROM TIME";
    return jdbcTemplate.queryForStream(sql, (rs, rowNum) -> Time.builder()
            .id(rs.getLong("id"))
            .time(rs.getString("time"))
            .build())
        .toList();
  }

  public Time save(String time) {
    String sql = "INSERT INTO TIME (time) VALUES(?)";

    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
      preparedStatement.setString(1, time);
      return preparedStatement;
    }, keyHolder);

    return Time.builder()
        .id(keyHolder.getKey().longValue())
        .time(time)
        .build();
  }

  public void deleteById(Long id) {
    String sql = "DELETE FROM TIME WHERE id = ?";
    jdbcTemplate.update(sql, id);
  }
}
