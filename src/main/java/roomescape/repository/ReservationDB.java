package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Repository
public class ReservationDB {
    // 6단계 : JdbcTemplate을 이용하여 DataSource객체에 접근
    private JdbcTemplate jdbcTemplate;

    public ReservationDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ReservationRowmapper 클래스가 RowMapper 인터페이스를 구현
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

    // 6단계 : 데이터 조회하기 : DB에서 전체 데이터 조회하기
    public List<Reservation> getReservationByDB() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, new ReservationRowmapper());
    }


    // 6단계 : 데이터 조회하기 : 특정 데이터 DB에서 조회하기 (id 활용)
    public Reservation getReservationByDB(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ReservationRowmapper(), id);
    }

    // 7단계 : DB에 예약 정보 추가하기
    public Reservation saveReservationDB(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        // KeyHolder 객체를 이용하여 생성된 id값을 받아옴
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, String.valueOf(reservation.getDate()));
            ps.setString(3, String.valueOf(reservation.getTime()));
            return ps;
        }, keyHolder);

        // 생성된 id값을 이용하여 Reservation 객체를 생성하여 반환
        return Reservation.builder()
                .id(requireNonNull(keyHolder.getKey()).longValue())
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .build();
    }


    // 7단계 : 예약 취소
    public void deleteReservationDB(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
