package roomescape.DAO;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.Domain.Reservation;
import roomescape.Domain.Time;

@Repository
public class ReservationDAO {

  private final JdbcTemplate jdbcTemplate;

  public ReservationDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Reservation> findAllReservations() {
    String sql = "SELECT r.id as reservation_id, r.name,  r.date, t.id as time_id, t.time as time_value "
        + "FROM reservation as r inner join time as t on r.time_id = t.id";
    return jdbcTemplate.query(
        sql,
        reservationRowMapper
        );
  }

  public Reservation findReservationByID(Long id) {
    String sql = "SELECT r.id as reservation_id, r.name,  r.date, t.id as time_id, t.time as time_value "
        + "FROM reservation as r inner join time as t on r.time_id = t.id WHERE r.id = ?";
    return jdbcTemplate.queryForObject(
        sql,
        reservationRowMapper,
        id
    );
  }

  public List<Reservation> findReservationsByID(Long id) {
    String sql = "SELECT r.id as reservation_id, r.name,  r.date, t.id as time_id, t.time as time_value "
        + "FROM reservation as r inner join time as t on r.time_id = t.id WHERE r.id = ?";
    return jdbcTemplate.query(
        sql,
        reservationRowMapper,
        id);
  }

  public Long insertWithKeyHolder(Reservation reservation) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
      ps.setString(1, reservation.getName());
      ps.setString(2, reservation.getDate());
      ps.setLong(3, reservation.getTime().getId());
      return ps;
    }, keyHolder);

    return keyHolder.getKey().longValue();
  }

  public int delete(Long id) {
    String sql = "delete from reservation where id = ?";
    return jdbcTemplate.update(sql, Long.valueOf(id));
  }

  private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
    return new Reservation(
        resultSet.getLong("id"),
        resultSet.getString("name"),
        resultSet.getString("date"),
        new Time(
            resultSet.getLong("time_id"),
            resultSet.getString("time_value")
        )
    );
  };
}
