package roomescape.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.dto.TimeRequestDto;
import roomescape.repository.TimeDao;

import java.net.URI;
import java.util.List;

@RestController
public class TimeController {

    private final TimeDao timeDao;

    public TimeController(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> readAll() {
        return ResponseEntity.ok().body(timeDao.findAllTimes());
    }

    @PostMapping("/times")
    public ResponseEntity<Time> create(@Valid @RequestBody TimeRequestDto requestDto) {
        Time time = new Time(null, requestDto.getTime());
        Long id = timeDao.insert(time);

        Time newTime = new Time(id, time.getTime());

        return ResponseEntity.created(URI.create("/times/" + id)).body(newTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeDao.deleteTime(id);

        return ResponseEntity.noContent().build();
    }
}
