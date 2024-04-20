package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.List;

@Controller
public class RoomescapeController {

  // private List<Reservation> reservations = new ArrayList<>();
  @Autowired
  private JdbcTemplate jdbcTemplate;
  @GetMapping("/")
  public String home() {
    return "home";
  }
  @GetMapping("/time")
  public String time() { return "time"; }

  @GetMapping("/reservation")
  public String reservation() {
    return "new-reservation";
  }

  @GetMapping("/reservations")
  @ResponseBody
  public List<Reservation> reservationList() {
    String sql = "select r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value from reservation as r inner join time as t on r.time_id = t.id";
    List<Reservation >reservations = jdbcTemplate.query(sql, (result, row) -> {
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

  @PostMapping("/reservations")
  @ResponseBody
  public ResponseEntity<Reservation> newReservation(@RequestBody Reservation reservation) {
    if (reservation.getDate().isEmpty() || reservation.getName().isEmpty() || reservation.getTime().getId() == null) {
      return ResponseEntity.badRequest().build();
    }

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
    Reservation added = new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    return ResponseEntity.created(URI.create("/reservations/" + id)).body(added);
  }

  @DeleteMapping("/reservations/{id}")
  public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
    String sql = "delete from reservation where id = ?";
    try {
      jdbcTemplate.queryForObject("select id from reservation where id = ?", Long.class, id);
      jdbcTemplate.update(sql, id);
    } catch (IncorrectResultSizeDataAccessException error) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.noContent().build();
  }
}
