package roomescape.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.mapper.DtoMapper;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

@Controller
public class ReservationController {

    private final ReservationRepository repository;

    private final AtomicLong idMaker = new AtomicLong();

    public ReservationController(final ReservationRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/reservation")
    public String getReservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponseDto> getReservations() {
        return repository.findAll().stream()
                .map(DtoMapper::convertToDTO)
                .toList();
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> saveReservation(@RequestBody ReservationRequestDto dto) {
        final Reservation reservation = new Reservation(
                dto.getName(),
                dto.getDate(),
                dto.getTime());
        final Reservation savedReservation = repository.save(reservation);
        final ReservationResponseDto responseDto = DtoMapper.convertToDTO(savedReservation);
        return ResponseEntity
                .created(URI.create("/reservations/" + responseDto.getId()))
                .body(responseDto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 테스트 데이터 삽입용 메서드
    private List<Reservation> createReservations() {

        return List.of(
                new Reservation(idMaker.incrementAndGet(), "해쉬", LocalDate.now(), LocalTime.now()),
                new Reservation(idMaker.incrementAndGet(), "브라운", LocalDate.now(), LocalTime.now()),
                new Reservation(idMaker.incrementAndGet(), "버거", LocalDate.now(), LocalTime.now())
        );
    }

}
