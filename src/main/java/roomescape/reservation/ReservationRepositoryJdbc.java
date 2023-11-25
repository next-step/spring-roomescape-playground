package roomescape.reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepositoryJdbc implements ReservationRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public Reservation save(String name, String date, String time) {
    String sql = "INSERT INTO RESERVATION (name, date, time) VALUES (?, ?, ?)";

    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
      preparedStatement.setString(1, name);
      preparedStatement.setObject(2, date);
      preparedStatement.setObject(3, time);
      return preparedStatement;
    }, keyHolder);

    return Reservation.builder()
        .id(keyHolder.getKey().longValue())
        .name(name)
        .date(date)
        .time(time)
        .build();
  }

  public List<Reservation> findAll() {
    String sql = "SELECT * FROM RESERVATION";
    return jdbcTemplate.queryForStream(sql, (rs, rowNum) -> Reservation.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .date(rs.getDate("date").toString())
            .time(rs.getTime("time").toString())
            .build())
        .toList();
  }

  public Optional<Reservation> findById(final Long id) {
    String sql = "SELECT * FROM RESERVATION WHERE id = ?";
    return jdbcTemplate.queryForStream(sql, (rs, rowNum) -> Reservation.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .date(rs.getDate("date").toString())
                .time(rs.getTime("time").toString())
                .build(),
            id)
        .findFirst();
  }

  public void deleteById(final Long id) {
    String sql = "DELETE FROM RESERVATION WHERE id = ?";
    int rowCount = jdbcTemplate.update(sql, id);

  }
}

