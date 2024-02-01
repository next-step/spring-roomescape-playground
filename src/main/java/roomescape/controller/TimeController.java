package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.exception.BadRequestReservationException;
import roomescape.model.dto.TimeDto;
import roomescape.model.entity.Time;
import roomescape.repository.TimeRepository;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {

    private final TimeRepository timeRepository;

    @Autowired
    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimes() {
        return ResponseEntity.ok(this.timeRepository.findAll());
    }

    @PostMapping("/times")
    public ResponseEntity<Time> addTime(@Valid @RequestBody TimeDto timeDto) {
        Time time = this.timeRepository.save(timeDto.toEntity());
        return ResponseEntity
                .created(URI.create("/times/" + time.getId()))
                .body(time);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") Long id) {
        if (this.timeRepository.delete(id) == 0)
            throw new BadRequestReservationException();
        return ResponseEntity.noContent().build();
    }
}
