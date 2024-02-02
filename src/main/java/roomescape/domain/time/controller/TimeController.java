package roomescape.domain.time.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.time.dto.request.TimeCreateRequestDto;
import roomescape.domain.time.entity.Time;
import roomescape.domain.time.repository.TimeDao;
import roomescape.exception.custom.BusinessException;

import java.net.URI;
import java.util.List;

import static roomescape.exception.ErrorCode.*;

@Controller
@RequestMapping("/times")
public class TimeController {
    private final TimeDao timeDao;

    public TimeController(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @PostMapping
    public ResponseEntity<Time> createTime(@RequestBody TimeCreateRequestDto requestDto) {
        Long timeId = timeDao.insert(requestDto);
        return ResponseEntity.created(URI.create("/times/" + timeId)).body(requestDto.toEntity(timeId));
    }

    @GetMapping
    public ResponseEntity<List<Time>> readTime() {
        return ResponseEntity.ok().body(timeDao.findAllTimes());
    }

    @DeleteMapping("/{timeId}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long timeId) {
        timeDao.findAllTimes().stream()
                .filter(time -> time.getId().equals(timeId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(TIME_NOT_FOUND));

        timeDao.deleteTimeById(timeId);
        return ResponseEntity.noContent().build();
    }
}