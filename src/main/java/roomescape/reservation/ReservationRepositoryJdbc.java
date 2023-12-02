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

  public Reservation save(String name, String date, Time time) {
    String sql = "INSERT INTO RESERVATION (name, date, time) VALUES (?, ?, ?)";

    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
      preparedStatement.setString(1, name);
      preparedStatement.setString(2, date);
      preparedStatement.setTime(3, time);
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
    String sql = "SELECT \n"
        + "    r.id as reservation_id, \n"
        + "    r.name, \n"
        + "    r.date, \n"
        + "    t.id as time_id, \n"
        + "    t.time as time_value \n"
        + "FROM reservation as r inner join time as t on r.time_id = t.id";
    return jdbcTemplate.queryForStream(sql, (rs, rowNum) -> Reservation.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .date(rs.getDate("date").toString())
            .time(rs.getTime("time"))
            .build())
        .toList();
  }

  public void deleteById(Long id) {
    String sql = "DELETE FROM RESERVATION WHERE id = ?";
    jdbcTemplate.update(sql, id);
  }
}

