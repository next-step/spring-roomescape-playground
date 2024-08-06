package roomescape.domain.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationRepository { // reservation 테이블에 대한 CRUD를 담당

  private final JdbcTemplate jdbcTemplate;

  public ReservationRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
    return new Reservation(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getDate("date").toLocalDate(),
        rs.getTime("time").toLocalTime());
  }; // RowMapper를 이용해 ResultSet에서 원하는 데이터를 추출

  public List<Reservation> findAll() {
    final String sql = "select id, name, date, time from reservation";
    return jdbcTemplate.query(sql, reservationRowMapper);
  }
}
