package roomescape;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
  private final JdbcTemplate jdbcTemplate;

  private final RowMapper<Reservation> rowMapper = (rs, rowNum) ->
      new Reservation(
          rs.getLong("id"),
          rs.getString("name"),
          rs.getString("date"),
          rs.getString("time")
      );

  public List<Reservation> getReservations() {
    String sql = "SELECT * FROM reservation";
    return jdbcTemplate.query(sql, rowMapper);
  }

  public Long addReservation(String name, String date, String time) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
    jdbcTemplate.update(
        connection -> {
          PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
          ps.setString(1, name);
          ps.setString(2, date);
          ps.setString(3, time);
          return ps;
        }, keyHolder);

    return Objects.requireNonNull(keyHolder.getKey()).longValue();
  }

  public boolean deleteReservation(Long id) {
    String sql = "DELETE FROM reservation WHERE id = ?";
    int rowsAffected = jdbcTemplate.update(sql, id);
    return rowsAffected > 0;
  }
}
