package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.entity.Time;

@Repository
public class ReservationRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;

  private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) ->
      Reservation.create(
          resultSet.getLong("id"),
          resultSet.getString("name"),
          resultSet.getString("date"),
          Time.create(
              resultSet.getLong("time_id"),
              resultSet.getString("time_value"))
      );


  public List<Reservation> findAll() {
    String selectSql = """
        SELECT
            r.id as reservation_id,
            r.name,
            r.date,
            t.id as time_id,
            t.time as time_value
        FROM reservation as r inner join time as t on r.time_id = t.id
        """;
    return jdbcTemplate.query(selectSql, rowMapper);
  }

  public Reservation create(Reservation reservation) {
    KeyHolder keyHolder = new GeneratedKeyHolder();

    String insertSql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
      ps.setString(1, reservation.getName());
      ps.setString(2, reservation.getDate());
      ps.setString(3, String.valueOf(reservation.getTime().getId()));
      return ps;
    }, keyHolder);

    Long generatedId = keyHolder.getKey().longValue();
    return Reservation.create(
        generatedId,
        reservation.getName(),
        reservation.getDate(),
        reservation.getTime()
    );
  }

  public void deleteById(Long id) {
    int rowsAffected = jdbcTemplate.update("delete from reservation where id = ?", id);

    if (rowsAffected == 0) {
      throw new NoSuchElementException();
    }
  }
}
