package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import roomescape.Reservation;
import roomescape.ReservationRequest;
import roomescape.exception.BadRequestException;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ApiController {

    private final ReservationRepository reservationRepository;

    public ApiController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // 6단계: 데이터베이스 기반 예약 목록 조회 API
    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    // 7단계: 데이터베이스 기반 예약 추가 API (KeyHolder 사용)
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody @Valid ReservationRequest request) {

        if (reservationRepository.countByDateAndTime(request.getDate(), request.getTime()) > 0) {
            throw new BadRequestException("이미 해당 시간대에 예약이 존재합니다.");
        }

        Long generatedId = reservationRepository.save(request.getName(), request.getDate(), request.getTime());

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
    @Transactional
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        if (reservationRepository.countById(id) == 0) {
            throw new NotFoundReservationException("삭제할 예약을 찾을 수 없습니다.");
        }

        reservationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
