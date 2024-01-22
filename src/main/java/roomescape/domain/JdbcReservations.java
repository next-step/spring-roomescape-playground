package roomescape.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class JdbcReservations {

    private final AtomicLong ids;
    private final List<Reservation> reservations;
    private final JdbcTemplate jdbcTemplate;


    public JdbcReservations(JdbcTemplate jdbcTemplate) {
        this.ids = new AtomicLong();
        this.reservations = new ArrayList<>();
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation add(Reservation reservation) {
        Reservation saved = reservation.with(ids.incrementAndGet());
        reservations.add(saved);
        return saved;
    }

    public List<Reservation> add(Reservation... reservations) {
        List<Reservation> saved = new ArrayList<>();
        for (Reservation reservation : reservations) {
            saved.add(add(reservation));
        }
        return saved;
    }

    public List<Reservation> getAll() {
        return jdbcTemplate.queryForStream(
                "SELECT id, name, date, time FROM reservation",
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("time")
                )
        ).toList();
    }

    public void cancel(Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("id가 " + id + "인 예약을 찾을 수 없습니다"));
        reservations.remove(reservation);
    }
}
