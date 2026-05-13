package roomescape;

import java.net.URI;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.exception.DuplicateReservationException;
import roomescape.exception.NotFoundReservationException;

@RestController
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);
    private final JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        log.info("예약 생성 요청: name={}, date={}, time={}", reservation.getName(), reservation.getDate(), reservation.getTime());

        String checkSql = "SELECT count(1) FROM reservation WHERE date = ? AND time = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class,
                reservation.getDate().toString(), reservation.getTime().toString());

        if (count != null && count > 0) {
            throw new DuplicateReservationException(reservation.getDate().toString(), reservation.getTime().toString());
        }

        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        }, keyHolder);

        Long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        Reservation newReservation = Reservation.toEntity(reservation, generatedId);

        return ResponseEntity.created(URI.create("/reservations/" + generatedId)).body(newReservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation.Response>> readAll() {
        log.info("전체 예약 조회 요청");
        String sql = "SELECT id, name, date, time FROM reservation";

        List<Reservation.Response> responseList = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Reservation reservation = new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    LocalDate.parse(rs.getString("date")),
                    LocalTime.parse(rs.getString("time"))
            );
            return new Reservation.Response(reservation);
        });

        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("예약 삭제 요청: id={}", id);

        String sql = "DELETE FROM reservation WHERE id = ?";
        int updatedRows = jdbcTemplate.update(sql, id);

        if (updatedRows == 0) {
            throw new NotFoundReservationException(id);
        }

        return ResponseEntity.noContent().build();
    }
}