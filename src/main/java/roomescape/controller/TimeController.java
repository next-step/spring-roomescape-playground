package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.exception.NotFoundTimeException;
import roomescape.repository.TimeRepository;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {
    private static final int NO_ROWS = 0;
    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }
    @PostMapping("/times")
    public ResponseEntity<TimeResponse> addTime(@RequestBody TimeRequest request) {
        Time time = request.toEntity();
        Time savedTime = timeRepository.save(time);

        return ResponseEntity.created(URI.create("/times/" + savedTime.getId()))
                .body(TimeResponse.from(savedTime));
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> readTimeList() {
        List<TimeResponse> timeResponses = timeRepository.findAll()
                .stream()
                .map(TimeResponse::from)
                .toList();
        return ResponseEntity.ok(timeResponses);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        int deletedCount = timeRepository.deleteById(id);

        if (deletedCount == NO_ROWS) {
            throw new NotFoundTimeException("삭제할 시간을 찾을 수 없습니다.");
        }
        return ResponseEntity.noContent().build();
    }
}
