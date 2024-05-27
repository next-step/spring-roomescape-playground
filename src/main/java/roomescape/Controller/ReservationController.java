package roomescape.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.DTO.ReservationResponse;
import roomescape.DTO.SaveReservationRequest;
import roomescape.Model.JdbcReservationRepository;
import roomescape.Model.JdbcTimeRepository;
import roomescape.Model.Reservation;
import roomescape.Model.Time;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {

    @Autowired
    private final JdbcReservationRepository jdbcReservationRepository;

    @Autowired
    private final JdbcTimeRepository jdbcTimeRepository;

    public ReservationController(JdbcReservationRepository jdbcReservationRepository, JdbcTimeRepository jdbcTimeRepository){
        this.jdbcReservationRepository = jdbcReservationRepository;
        this.jdbcTimeRepository = jdbcTimeRepository;
    }


    @RequestMapping("/reservation")
    public String reservation() {
        return "new-reservation";
    }

    // 예약 목록 조회
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> read() {
        List<Reservation> reservations = jdbcReservationRepository.findAll();
        List<ReservationResponse> response = reservations.stream().map(ReservationResponse::from).toList();
        return ResponseEntity.ok(response);
    }

    // id에 따른 예약 조회
    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponse> readEach(@PathVariable Long id) {
        Reservation reservation = jdbcReservationRepository.findWithId(id);
        ReservationResponse response = ReservationResponse.from(reservation);
        return ResponseEntity.ok(response);
    }

    // 예약 생성
    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> create(@RequestBody SaveReservationRequest request) {
        final Time time = jdbcTimeRepository.findById(request.time());

        Reservation reservation = jdbcReservationRepository.save(new Reservation(request.name(), request.date(), time));
        ReservationResponse response = ReservationResponse.from(reservation);
        String uri = "/reservations/" + reservation.getId();
        return ResponseEntity.created(URI.create(uri)).body(response);
    }

    // 예약 삭제
    @DeleteMapping("reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jdbcReservationRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
