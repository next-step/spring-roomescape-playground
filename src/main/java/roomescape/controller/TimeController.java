package roomescape.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeDTO;
import roomescape.entity.Time;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Slf4j
@Controller
public class TimeController {

    @Autowired
    private TimeService timeService;

    @GetMapping("/times")
    public ResponseEntity<List<Time>> read() {
        // TODO: 저장된 모든 time 정보를 반환한다.
        return ResponseEntity.ok(timeService.findAllTimes());
    }

    @PostMapping("/times")
    public ResponseEntity<Time> create(@RequestBody TimeDTO timeDTO) {
        // TODO: time 정보를 받아서 생성한다.
       Time time = timeService.saveTime(timeDTO);
       return ResponseEntity.created(URI.create("/times/" + time.getId())).body(time);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // TODO: url 상의 id 정보를 받아 time을 삭제한다.
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }

}