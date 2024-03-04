package roomescape.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.DTO.TimeRequestDTO;
import roomescape.DTO.TimeResponseDTO;
import roomescape.Domain.Time;
import roomescape.Repository.TimeRepository;
import roomescape.Service.TimeServiceImpl;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/times")
public class TimeReservationController {
    private final TimeServiceImpl timeService;

    public TimeReservationController(TimeServiceImpl timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponseDTO>> getAllTimeReservations() {
        return ResponseEntity.ok(timeService.findAllTimeReservations());
    }

    @PostMapping
    public ResponseEntity<TimeResponseDTO> createTimeReservation(@RequestBody TimeRequestDTO timeRequest) {
        Long id = timeService.createTimeReservation(timeRequest);
        TimeResponseDTO newTimeReservation = timeService.findTimeReservationById(id);

        return ResponseEntity.created(URI.create("/times/" + newTimeReservation.getId())).body(newTimeReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeReservation(@PathVariable Long id) {
        timeService.deleteTimeReservationById(id);
        return ResponseEntity.noContent().build();
    }
}
