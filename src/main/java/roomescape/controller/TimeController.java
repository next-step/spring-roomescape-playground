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
import roomescape.entity.Dto.TimeInDto;
import roomescape.entity.repository.TimeRepository;
import roomescape.entity.value.Time;
import roomescape.exception.NotFoundException;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping
    public List<Time> getTimes() {
        return timeRepository.findAll();
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Time> createTime(@RequestBody TimeInDto timeInDto) {
        final Time save = timeRepository.save(timeInDto);
        URI location = URI.create("/times/" + save.getId());
        return ResponseEntity.created(location).body(save);
    }


    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        final int countOfDeleted = timeRepository.deleteById(id);

        if (countOfDeleted <= 0) {
            throw new NotFoundException("해당 id를 가진 Time 객체를 찾을 수 없습니다.");
        }

        return ResponseEntity.noContent().build();
    }

}
