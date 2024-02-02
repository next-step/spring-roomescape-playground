package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.controller.dto.CreateTimeDto;
import roomescape.controller.dto.TimeDto;
import roomescape.repository.TimeDao;

import java.net.URI;

@Controller
public class TimeController {
    private final TimeDao timeDao;

    TimeController(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @PostMapping("/times")
    public ResponseEntity<TimeDto> addTime(@Validated @RequestBody CreateTimeDto dto) {
        TimeDto savedTime = TimeDto.toDto(timeDao.save(dto));
        return ResponseEntity.created(URI.create("/times/" + savedTime.getId())).body(savedTime);
    }
}
