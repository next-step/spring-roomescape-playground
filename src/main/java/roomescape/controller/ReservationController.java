package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReadReservationResponse;
import org.springframework.web.bind.annotation.*;


import java.net.URI;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import static java.lang.String.valueOf;


@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);


    @GetMapping("/reservation")
    public String reservation() {
        return "reservation.html";
    }


    // 예약 목록 조회 API
    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReadReservationResponse>> readAll() {
        final List<ReadReservationResponse> responses = reservations.stream()
                .map(ReadReservationResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    //예약 추가

    public ResponseEntity<Reservation> create(@RequestBody Reservation request) {
        long id = index.incrementAndGet();

        Reservation newReservation = new Reservation(id, request.getName(), request.getDate(), request.getTime());
        reservations.add(newReservation);

        URI location = URI.create("/reservations/" + id);

        return ResponseEntity.created(location).body(newReservation);
    }

    // 예약 삭제


    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (reservations.stream().noneMatch(reservation -> reservation.getId().equals(id))) {
            return ResponseEntity.notFound().build();
        }

        for (Reservation reservation : this.reservations) {
            if (reservation.getId().equals(id)) { // class 비교할 때는 equals 메서드를 씀
                this.reservations.remove(reservation);
                break;
            }
        }
        return ResponseEntity.noContent().build();
    }



//데이터베이스 조회
    private JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {

        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("time"),
                resultSet.getString("date")
        );
        return reservation;
    };

    public List<Reservation> findReservation(){
        String sql = "SELECT id, name, date, time FROM reservation";
        List<Reservation> reservationList = jdbcTemplate.query(sql, reservationRowMapper);
        return reservationList;
    }



// 데이터베이스 수정, 삭제

    @PostMapping("/reservations") // @PostMapping("/reservations"): 1. HTTP 메서드 POST이고, 2.괄호 안에 URL = /reservations 일 때 요청 처리를 해라!! 근데 HTTP와 URL이 둘 다 같은 데 두 개 이상 있으면 에러!
    @ResponseBody
    public ResponseEntity<Reservation> newReservation(@RequestBody Reservation reservation){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql,
                    new String[]{"id"}
            );
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, reservation.getTime());
            return ps;

        }, keyHolder);
        Long id = keyHolder.getKey().longValue();
        Reservation added = new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getName());
        return ResponseEntity.created(URI.create("/reservations/" + id)).body(added);
    }


    @DeleteMapping("/reservations/{id}") // 여기도 마찬가지. HTTP 메서드가 Delete 이고, URL이 /reservations/{id} 일 때 요청 처리를 해라.
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) throws Exception {

        // 요청으로 들어온 id의 reservation이 없는 경우 기존 로직은 예외를 던진다.
        String query = "SELECT COUNT(*) FROM reservation WHERE id = ?";
        int count = jdbcTemplate.queryForObject(query, Integer.class, id);
        if (count == 0) {
            throw new Exception("해당 id가 없습니다.");
        }

        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.noContent().build();
    }





    
    
    
    

}