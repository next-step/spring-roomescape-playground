package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequestDto;

@Repository
@RequiredArgsConstructor
public class ReservationDao {

  private final JdbcTemplate jdbcTemplate;

  public List<Reservation> findAll() {
    return jdbcTemplate.query("SELECT "
        + "r.id AS reservation_id, "
        + "r.name, "
        + "r.date, "
        + "t.id AS time_id, "
        + "t.time AS time_value "
        + "FROM reservation AS r INNER JOIN time AS t ON r.time_id = t.id", (rs, rowNum) ->
        new Reservation(
            rs.getLong("reservation_id"),
            rs.getString("name"),
            rs.getString("date"),
            new Time(rs.getLong("time_id"), rs.getString("time_value"))
        ));
  }

  public Reservation save(ReservationRequestDto reservationRequestDto) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(
          "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
          new String[]{"id"}
      );
      ps.setString(1, reservationRequestDto.getName());
      ps.setString(2, reservationRequestDto.getDate());
      ps.setLong(3, Long.parseLong(reservationRequestDto.getTime()));
      return ps;
    }, keyHolder);

    return new Reservation(keyHolder.getKey().longValue(), reservationRequestDto.getName(), reservationRequestDto.getDate());
  }

  public int delete(Long id) {
    return jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
  }
}
