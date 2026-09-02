package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.TimeDAO;
import roomescape.domain.time.TimeRequest;
import roomescape.domain.time.Time;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class TimeController {
    private final TimeDAO timeDAO;

    public TimeController(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> read() {
        return ResponseEntity.ok(timeDAO.findAllTimes());
    }

    @PostMapping("/times")
    public ResponseEntity<Time> create(@Valid @RequestBody TimeRequest request) {
        Long generatedId = timeDAO.insertWithKeyHolder(request);
        Time newTime = Time.toEntity(request, generatedId);

        return ResponseEntity.created(URI.create("/times/" + newTime.getId())).body(newTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int deletedCount = timeDAO.delete(id);
        if (deletedCount == 0) {
            throw new NoSuchElementException("해당 시간을 찾을 수 없습니다");
        }
        return ResponseEntity.noContent().build();
    }
}
