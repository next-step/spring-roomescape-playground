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
    private final TimeServiceImpl service;

    public TimeReservationController(TimeServiceImpl service)
    {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponseDTO>> getAllTimeReservations() {
        return ResponseEntity.ok(service.findAllTimeReservations());
    }

    /*
    @GetMapping("/{id}")
    public Time getTimeReservation(@PathVariable Long id) {
        return timeDAO.findTimeReservation(id);
    }
    */

    @PostMapping
    public ResponseEntity<TimeResponseDTO> createTimeReservation(@RequestBody TimeRequestDTO timeRequest)
    {
        Long id = service.createTimeReservation(timeRequest);
        TimeResponseDTO newTimeReservation = service.findTimeReservationById(id);

        return ResponseEntity.created(URI.create("/times/" + newTimeReservation.getId())).body(newTimeReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeReservation(@PathVariable Long id)
    {
        service.deleteTimeReservationById(id);
        return ResponseEntity.noContent().build();
    }
}
