package roomescape.dao;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.exception.ReservationSaveFailedException;

@Repository
public class ReservationTimeDao {

  private final JdbcTemplate jdbcTemplate;

  public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private final RowMapper<ReservationTime> reservationTimeRowMapper = (rs, rowNum) ->
      new ReservationTime(rs.getLong("id"),
          LocalTime.parse(rs.getString("time"))
      );

  public List<ReservationTime> findAll() {
    return jdbcTemplate.query("SELECT * FROM reservation_time", reservationTimeRowMapper);
  }

  public Optional<ReservationTime> findById(Long id) {
    return jdbcTemplate.query("SELECT * FROM reservation_time WHERE id=?", reservationTimeRowMapper, id)
        .stream().findFirst();
  }

  public ReservationTime save(ReservationTime reservationTime) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(
          "INSERT INTO reservation_time(time) VALUES (?)",
          new String[]{"id"});
      ps.setString(1, reservationTime.getTime().toString());
      return ps;
    }, keyHolder);

    return new ReservationTime(extractGeneratedId(keyHolder), reservationTime.getTime());
  }

  private Long extractGeneratedId(KeyHolder keyHolder) {
    Number key = keyHolder.getKey();
    if (key == null) {
      throw new ReservationSaveFailedException("예약시간 생성 중 id를 발급받지 못했습니다.");
    }
    return key.longValue();
  }

  public int delete(Long id) {
    return jdbcTemplate.update("DELETE FROM reservation_time WHERE id=?", id);
  }
}
