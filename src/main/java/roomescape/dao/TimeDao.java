package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

@Repository
@RequiredArgsConstructor
public class TimeDao {

  private final JdbcTemplate jdbcTemplate;

  private final RowMapper<Time> rowMapper = (resultSet, rowNum) -> {

    return Time.builder()
        .id(resultSet.getLong("id"))
        .time(resultSet.getString("time"))
        .build();
  };


  public Optional<Time> findById(Long id) {
    return jdbcTemplate.query(
        "select id, time from time where id = ?", rowMapper, id
    ).stream().findFirst();
  }

  public List<Time> findAll() {
    return jdbcTemplate.query(
        "select id, time from time", rowMapper
    );
  }

  public Time save(Time time) {
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(
          "INSERT INTO time (time) values (?)",
          new String[]{"id"}
      );
      ps.setString(1, time.getTime().toString());
      return ps;
    }, keyHolder);
    Long id = keyHolder.getKey().longValue();
    time.generateId(id);

    return time;
  }

  public void remove(Time time) {
    jdbcTemplate.update(
        "delete from time where id = ?", time.getId()
    );
  }

}