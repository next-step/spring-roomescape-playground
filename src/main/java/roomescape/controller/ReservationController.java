package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationCreatequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    // 1. final 서비스 선언
    private final ReservationService service;

    // 2. 생성자 주입 (Spring이 '진짜' 서비스 빈을 넣어줌)
    @Autowired
    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReservationResponse> getAllReservations() {
        return  service.getAllReservations().stream()
                .map(ReservationResponse::from) // (::from은 ReservationResponse::from과 동일)
                .toList();
    }


    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody ReservationCreatequest requestDto
    ) {

        Reservation reservationToCreate = new Reservation( // (변수명 변경)
                requestDto.name(),
                requestDto.date(),
                requestDto.time()
        );

        // 서비스가 'id'가 발급된 객체를 반환
        Reservation savedReservation = service.addReservation(reservationToCreate);

        // 'savedReservation' (id 있음)을 사용하여 응답 생성
        ReservationResponse responseDto = ReservationResponse.from(savedReservation);
        URI location = URI.create("/reservations/" + savedReservation.getId());

        return ResponseEntity.created(location).body(responseDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id
    ) {
        boolean removed = service.deleteReservation(id);

        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
