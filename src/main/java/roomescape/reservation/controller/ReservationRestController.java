package roomescape.reservation.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.exception.InvalidReservationRequestException;
import roomescape.reservation.exception.ReservationNotFoundException;
import roomescape.reservation.model.Reservation;
import roomescape.reservation.request.ReservationRequest;
import roomescape.reservation.response.ReservationResponse;
import roomescape.time.model.Time;

@RestController
@RequestMapping("/reservations")
public class ReservationRestController {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRestController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        String sql =
            "select "
                + "r.id as reservation_id, "
                + "r.name, "
                + "r.date, "
                + "t.id as time_id, "
                + "t.time as time_value "
                + "from reservation as r "
                + "inner join time as t on r.time_id = t.id";

        List<Reservation> reservations = jdbcTemplate.query(
            sql,
            (resultSet, rowNum) -> {
                return new Reservation(
                    resultSet.getLong("reservation_id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getLong("time_id"),
                    resultSet.getTime("time_value").toLocalTime()
                );
            });

        return ResponseEntity.ok(
            reservations.stream()
                .map(ReservationResponse::new)
                .toList()
        );
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> postReservation(
        @RequestBody @Validated ReservationRequest request
    ) {
        validateRequest(request);

        Time time;
        try {
            time = jdbcTemplate.queryForObject(
                "select id, time from time where id = ?",
                (resultSet, rowNum) -> new Time(
                    resultSet.getLong("id"),
                    resultSet.getTime("time").toLocalTime()
                ),
                request.getTimeId()
            );

            if (time == null) {
                throw new InvalidReservationRequestException(
                    "Invalid time ID: " + request.getTimeId());
            }
        } catch (NumberFormatException | DataAccessException e) {
            throw new InvalidReservationRequestException(
                "Invalid time ID: " + request.getTimeId());
        }

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", request.getName());
        parameters.put("date", request.getDate());
        parameters.put("timeId", time.getId());
        parameters.put("timeValue", time.getTime());

        Long id = (Long) insert.executeAndReturnKey(parameters);

        Reservation reservation = new Reservation(
            id,
            request.getName(),
            request.getDate(),
            time.getId(),
            time.getTime()
        );

        // Location 헤더에 생성된 리소스의 URI 추가
        return ResponseEntity
            .created(URI.create("/reservations/" + id))  // created를 통해 201 상태 코드 + Location 설정
            .body(new ReservationResponse(reservation));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
        @PathVariable Long id
    ) {
        boolean removed = jdbcTemplate.update(
            "DELETE FROM reservation WHERE id = ?", id
        ) > 0;

        if (!removed) {
            throw new ReservationNotFoundException("Reservation not found with id: " + id);
        }
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    private void validateRequest(ReservationRequest request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new InvalidReservationRequestException("Name is required");
        }
        if (request.getDate() == null) {
            throw new InvalidReservationRequestException("Date is required");
        }
        if (request.getTimeId() == null) {
            throw new InvalidReservationRequestException("Time is required");
        }
    }
}
