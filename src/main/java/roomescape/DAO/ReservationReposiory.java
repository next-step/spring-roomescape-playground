package roomescape.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.DTO.ReservationDTO;
import roomescape.Domain.Reservation;
import roomescape.Domain.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationReposiory {
  @Autowired
  private JdbcTemplate jdbcTemplate;
  public List<Reservation> getAllReservation() {
    String sql = """
            select
             r.id as reservation_id,
             r.name,
             r.date,
             t.id as time_id,
             t.time as time_value
            from reservation as r
            inner join time as t on r.time_id = t.id
            """;
    List<Reservation> reservations = jdbcTemplate.query(sql, (result, row) -> {
      Reservation reservation = new Reservation(
              result.getLong("id"),
              result.getString("name"),
              result.getString("date"),
              new Time(result.getLong("time_id"), result.getString("time_value"))
      );
      return reservation;
    });
    return reservations;
  }

  public Reservation makeNewReservation(Reservation reservation) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
    jdbcTemplate.update(con -> {
      PreparedStatement ps = con.prepareStatement(
              sql,
              new String[]{"id"}
      );
      ps.setString(1, reservation.getName());
      ps.setString(2, reservation.getDate());
      ps.setLong(3, reservation.getTime().getId());
      return ps;
    }, keyHolder);
    Long id = keyHolder.getKey().longValue();
    Reservation newReservation = new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());

    return newReservation;
  }

  public void deleteReservationById(Long id) {
    String sql = "delete from reservation where id = ?";
    try {
      jdbcTemplate.queryForObject("select id from reservation where id = ?", Long.class, id);
      jdbcTemplate.update(sql, id);
    } catch (IncorrectResultSizeDataAccessException error) {
      error.printStackTrace();
    }
  }
}
