package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.InvalidTimeException;
import roomescape.model.Time;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/times")
public class TimeAPIController {
    private final TimeRepository timeRepository;

    public TimeAPIController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }
    @GetMapping
    public List<Time> getReservations() {
        return timeRepository.findAll();
    }
    @PostMapping
    public ResponseEntity<Time> addTime(@RequestBody Map<String,String> params){
        String t = params.get("time");
        if (t == null || t.isBlank()) {
            throw new InvalidTimeException("time값이 필요합니다.");
        }

        Time time = new Time(null,t);
        Time saved = timeRepository.save(time);

        return ResponseEntity
            .created(URI.create("/times/"+saved.getId()))
            .body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id){
        boolean deleted = timeRepository.deleteById(id);
        if (!deleted) {
            throw new InvalidTimeException("삭제 오류 발생");
        }
        return ResponseEntity.noContent().build();
    }
}
