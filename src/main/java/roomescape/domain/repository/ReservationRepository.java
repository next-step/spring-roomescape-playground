package roomescape.domain.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationRepository { // reservation 테이블에 대한 CRUD를 담당

  private static final int NO_ROWS_AFFECTED = 0;

  private final JdbcTemplate jdbcTemplate;

  public ReservationRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
      resultSet.getLong("id"),
      resultSet.getString("name"),
      resultSet.getDate("date").toLocalDate(),
      resultSet.getTime("time").toLocalTime()); // RowMapper를 이용해 ResultSet에서 원하는 데이터를 추출

  public List<Reservation> findAll() {
    final String sql = "select id, name, date, time from reservation";
    return jdbcTemplate.query(sql, reservationRowMapper);
  }

  public Reservation save(Reservation reservation) {
    final String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
      preparedStatement.setString(1, reservation.getName());
      preparedStatement.setString(2, reservation.getDate().toString());
      preparedStatement.setString(3, reservation.getTime().toString());
      return preparedStatement;
    }, keyHolder);
    return Reservation.of(keyHolder.getKey().longValue(), reservation);
  }

  public boolean deleteById(Long id) {
    final String sql = "delete from reservation where id = ?";
    return jdbcTemplate.update(sql, id) > NO_ROWS_AFFECTED;
  }
}
