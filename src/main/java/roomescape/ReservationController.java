package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.List;

@Controller
public class ReservationController {
//
//    private List<Reservation> reservations = new ArrayList<>();
//    private AtomicLong index = new AtomicLong(1);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    // 예약 목록 조회
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        String sql = "SELECT * FROM reservation";
        List<Reservation> reservations = jdbcTemplate.query(sql,
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getString("time")
                ));
        return ResponseEntity.ok().body(reservations);
    }

    // id에 따른 예약 조회
//    @GetMapping("/reservations/{id}")
//    public ResponseEntity<Reservation> readEach(@PathVariable Long id) {
//        Reservation reservation = reservations.stream()
//                .filter(it -> Objects.equals(it.getId(), id))
//                .findFirst()
//                .orElseThrow(NoSuchElementException::new);
//        return ResponseEntity.ok().body(reservation);
//    }

    // 예약 생성
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, reservation.getTime());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        reservation.setId(id);

        return ResponseEntity.created(URI.create("/reservations/" + id)).body(reservation);
    }

    // 예약 삭제
    @DeleteMapping("reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);

        return ResponseEntity.noContent().build();
    }
}
