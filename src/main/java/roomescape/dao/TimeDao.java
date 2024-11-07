package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.dto.TimeRequestDto;

@Repository
@RequiredArgsConstructor
public class TimeDao {
  private final JdbcTemplate jdbcTemplate;

  public List<Time> findAll() {
    return jdbcTemplate.query("SELECT * FROM time", (rs, rowNum) ->
        new Time(
            rs.getLong("id"),
            rs.getString("time")
        ));
  }

  public Optional<Time> findById(Long id) {
    try {
      Time time = jdbcTemplate.queryForObject("SELECT * FROM time WHERE id = (?)", (rs, rowNum) ->
          new Time(
              rs.getLong("id"),
              rs.getString("time")),
          id);
      return Optional.of(time);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Time save(TimeRequestDto timeRequestDto) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(
          "INSERT INTO time (time) VALUES (?)",
          new String[] {"id"}
      );
      ps.setString(1, timeRequestDto.getTime());
      return ps;
    }, keyHolder);

    return new Time(keyHolder.getKey().longValue(), timeRequestDto.getTime());
  }

  public int delete(Long id) {
    return jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
  }
}
