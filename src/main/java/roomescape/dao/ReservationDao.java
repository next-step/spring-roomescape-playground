package roomescape.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.exception.ReservationSaveFailedException;

@Repository
public class ReservationDao {

  private final JdbcTemplate jdbcTemplate;

  public ReservationDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) ->
      new Reservation(rs.getLong("reservation_id"),
          rs.getString("name"),
          LocalDate.parse(rs.getString("date")),
          new ReservationTime(rs.getLong("time_id"), LocalTime.parse(rs.getString("time_value")))
      );

  public List<Reservation> findAll() {
    return jdbcTemplate.query(
        "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.time AS time_value "
            + "FROM reservation AS r INNER JOIN reservation_time AS t ON r.time_id = t.id",
        reservationRowMapper);
  }

  public Reservation save(Reservation reservation) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(
          "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)",
          new String[]{"id"});
      ps.setString(1, reservation.getName());
      ps.setString(2, reservation.getDate().toString());
      ps.setLong(3, reservation.getTime().getId());
      return ps;
    }, keyHolder);

    return new Reservation(extractGeneratedId(keyHolder), reservation.getName(),
        reservation.getDate(), reservation.getTime());
  }

  private Long extractGeneratedId(KeyHolder keyHolder) {
    Number key = keyHolder.getKey();
    if (key == null) {
      throw new ReservationSaveFailedException("예약 생성 중 id를 발급받지 못했습니다.");
    }
    return key.longValue();
  }

  public int delete(Long id) {
    return jdbcTemplate.update("DELETE FROM reservation WHERE id=?", id);
  }
}
