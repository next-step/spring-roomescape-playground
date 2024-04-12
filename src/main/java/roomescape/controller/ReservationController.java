package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.Reservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@RestController
public class ReservationController {
    // 6단계 : JdbcTemplate을 이용하여 DataSource객체에 접근
    @Autowired
    private JdbcTemplate jdbcTemplate;

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


    // localhost:8080/reservations 요청 시 예약자 현황 리스트가 응답할 수 있도록 구현
    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return getReservationByDB();
    }

    // 예약 추가
    @PostMapping("/reservations")
    public  ResponseEntity<?> saveReservation(@RequestBody Reservation reservation) {
        // 예약 추가 시 필요한 인자값이 비어있는 경우, 예외를 던집니다.
        if (Objects.equals(reservation.getName(), "") || Objects.equals(reservation.getDate(), "") || Objects.equals(reservation.getTime(), "")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error! 예약 추가 시 필요한 인자값이 비어 있습니다.");
        }

        saveReservationDB(reservation);

        // 생성된 예약 정보와 함께 201 Created 응답 반환 (CREATED : 201, body : API 응답 정보 반환)
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + reservation.getId())
                .body(reservation);
    }

    // DB에 예약 정보 추가하기
    public void saveReservationDB(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        // KeyHolder을 통해 만든 key가 id값을 대체
        KeyHolder keyHolder = new GeneratedKeyHolder(); // KeyHolder 생성
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        reservation.setId(generatedId);
    }

    // 예약 취소
    @DeleteMapping("/reservations/{id}") // 'reservaions/1' 이런 식으로 맵핑함
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        // ID와 일치하는 예약을 찾아서 삭제합니다.
        Reservation existingReservation = getReservationByDB(id); // 삭제할 예약 정보 가져오기
        if(existingReservation != null) {
            String sql = "DELETE FROM reservation WHERE id = ?";
            jdbcTemplate.update(sql, id);
            return ResponseEntity.noContent().build();
        } else {
            // ID와 일치하는 예약을 찾지 못한 경우, 예외를 던집니다.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error! 회원 ID와 일치하는 예약을 찾지 못했습니다.");
        }
    }

}