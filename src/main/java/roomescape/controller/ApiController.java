package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import roomescape.Reservation;
import roomescape.ReservationRequest;
import roomescape.exception.BadRequestException;
import roomescape.exception.NotFoundReservationException;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ApiController {

    private final JdbcTemplate jdbcTemplate; //자바 코드에서 SQL을 실행하게 해주는 도구

    // 데이터베이스 접근을 위한 JdbcTemplate 주입 (메모리 자료구조 영구 제거)
    public ApiController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 데이터베이스 조회 결과를 Reservation 객체로 매핑하는 규칙 정의
    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("date"),
            rs.getString("time")
    );

    // 6단계: 데이터베이스 기반 예약 목록 조회 API
    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // 7단계: 데이터베이스 기반 예약 추가 API (KeyHolder 사용)
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody @Valid ReservationRequest request) {

        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)"; //?는 나중에 값이 들어갈 자리
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, request.getName()); //각 물음표 몇번째 자리에 어떤 값을 넣을지
            ps.setString(2, request.getDate());
            ps.setString(3, request.getTime());
            return ps;
        }, keyHolder);

        // 생성된 고유 ID값 추출
        Long generatedId = keyHolder.getKey().longValue();

        Reservation reservation = new Reservation(
                generatedId,
                request.getName(),
                request.getDate(),
                request.getTime()
        );

        return ResponseEntity
                .created(URI.create("/reservations/" + generatedId))
                .body(reservation);
    }

    // 7단계: 데이터베이스 기반 예약 취소 API
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        // 데이터가 존재하는지 확인 후 카운트가 0이면 예외 발생
        String checkSql = "SELECT count(1) FROM reservation WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, id);

        if (count == null || count == 0) {
            throw new NotFoundReservationException("삭제할 예약을 찾을 수 없습니다.");
        }

        String deleteSql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(deleteSql, id);

        return ResponseEntity.noContent().build();
    }
}
