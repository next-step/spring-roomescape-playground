package roomescape.repository;

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
import roomescape.dto.ReservationRequest;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.ReservationSaveFailedException;

@Repository
public class ReservationRepository {

  private final JdbcTemplate jdbcTemplate;

  public ReservationRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) ->
      new Reservation(rs.getLong("id"),
          rs.getString("name"),
          LocalDate.parse(rs.getString("date")),
          LocalTime.parse(rs.getString("time"))
      );

  public List<Reservation> findAll() {
    return jdbcTemplate.query("SELECT * FROM reservation", reservationRowMapper);
  }

  public Reservation save(ReservationRequest reservationRequest) {
    Reservation.validate(reservationRequest.getName(), reservationRequest.getDate(),
        reservationRequest.getTime());

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(
          "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)",
          new String[]{"id"});
      ps.setString(1, reservationRequest.getName());
      ps.setString(2, reservationRequest.getDate().toString());
      ps.setString(3, reservationRequest.getTime().toString());
      return ps;
    }, keyHolder);

    return reservationRequest.toDomain(extractGeneratedId(keyHolder));
  }

  private Long extractGeneratedId(KeyHolder keyHolder) {
    Number key = keyHolder.getKey();
    if (key == null) {
      throw new ReservationSaveFailedException("예약 생성 중 id를 발급받지 못했습니다.");
    }
    return key.longValue();
  }

  public void delete(Long id) {
    int affectedRows = jdbcTemplate.update("DELETE FROM reservation WHERE id=?", id);
    if (affectedRows == 0) {
      throw new NotFoundReservationException("해당 id의 예약이 존재하지 않습니다.");
    }
  }
}
