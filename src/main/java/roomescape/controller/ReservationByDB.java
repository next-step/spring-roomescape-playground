package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import roomescape.dto.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationByDB {
    // JdbcTemplate을 이용하여 DataSource객체에 접근
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ReservationRowmapper 클래스가 RowMapper 인터페이스를 구현한다.
    public class ReservationRowmapper implements RowMapper<Reservation> {
        @Override
        public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String date = rs.getString("date");
            String time = rs.getString("time");

            return new Reservation(id, name, date, time);
        }
    }

    // 6단계 : 데이터 조회하기 : 전체 데이터 조회하기
    public List<Reservation> getReservationByDB() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, new ReservationRowmapper());
    }


    // 6단계 : 데이터 조회하기 : 특정 데이터 조회하기 (id 활용)
    public Reservation getReservationByDB(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ReservationRowmapper(), id);
    }

    //

}
