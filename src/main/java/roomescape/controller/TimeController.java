package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entity.dto.TimeCreateDto;
import roomescape.entity.value.Time;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final ReservationService reservationService;

    public TimeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Time> getTimes() {
        return reservationService.findAllTimes();
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Time> createTime(@RequestBody TimeCreateDto timeCreateDto) {
        final Time time = reservationService.saveTime(timeCreateDto);
        URI location = URI.create("/times/" + time.getId());
        return ResponseEntity.created(location).body(time);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        reservationService.deleteTimeById(id);
        return ResponseEntity.noContent().build();
    }

}
