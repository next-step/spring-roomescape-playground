package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.service.TimeService;
import roomescape.service.TimeServiceImpl;

@Repository
@RequiredArgsConstructor
public class ReservationDao {

  private final JdbcTemplate jdbcTemplate;

  private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {

    return Reservation.builder()
        .id(resultSet.getLong("reservation_id"))
        .name(resultSet.getString("name"))
        .date(resultSet.getString("date"))
        .time(
            Time.builder()
                .id(resultSet.getLong("time_id"))
                .time(resultSet.getString("time"))
                .build()
        )
        .build();
  };


  public Optional<Reservation> findById(Long id) {
    return jdbcTemplate.query(
        "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.time FROM reservation AS r JOIN time AS t ON (r.time_id = t.id AND r.id = ?)", rowMapper, id
    ).stream().findFirst();
  }

  public List<Reservation> findAll() {
    return jdbcTemplate.query(
        "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.time FROM reservation AS r JOIN time AS t ON r.time_id = t.id", rowMapper
    );
  }

  public Reservation save(Reservation reservation) {
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
          PreparedStatement ps = connection.prepareStatement(
              "INSERT INTO reservation (name, date, time_id) values (?, ?, ?)",
              new String[]{"id"}
          );
          ps.setString(1, reservation.getName());
          ps.setString(2, reservation.getDate());
          ps.setLong(3, reservation.getTime().getId());
          return ps;
        }, keyHolder);
    Long id = keyHolder.getKey().longValue();
    reservation.generateId(id);

    return reservation;
  }

  public void remove(Reservation reservation) {
    jdbcTemplate.update(
        "DELETE FROM reservation WHERE id = ?", reservation.getId()
    );
  }
}