package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.status.ReservationNotFoundException;

@Service
public class ReservationService {
    private final JdbcTemplate jdbcTemplate;

    public ReservationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = jdbcTemplate.query(
                "SELECT * FROM reservation",
                (rs, rowNum) -> Reservation.of(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime()
                )
        );

        return reservations.stream()
                .map(ReservationResponse::new)
                .toList();
    }

    public ReservationResponse create(ReservationRequest request) {
        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                request.getName(),
                request.getDate(),
                request.getTime()
        );

        Long id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM reservation", Long.class);
        Reservation saved = Reservation.of(id, request.getName(), request.getDate(), request.getTime());

        return new ReservationResponse(saved);
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}

