package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/reservations")
@Controller
public class ReservationController {
    private ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    //ResponseEntity -> @Responsebody,@RestControlller 역할
    // HTTP 응답을 나타내는 객체이며, 응답의 본문 데이터와 함께 HTTP 상태 코드, 헤더 등을 포함할 수 있습니다.
    @GetMapping()
    public ResponseEntity<List<ReservationResponse>> reservations(){
        List<ReservationResponse> response =reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<ReservationResponse> save(@RequestBody ReservationRequest request){
        Reservation reservation = reservationRepository.save(request.makeReservation());
        ReservationResponse response = ReservationResponse.from(reservation);
        URI location=URI.create("/reservations/" +response.id());
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reservationRepository.deleteByID(id);
        return ResponseEntity.noContent().build();
    }
}
