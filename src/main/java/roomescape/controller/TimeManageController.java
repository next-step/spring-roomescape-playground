package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.dto.TimeRequestDto;
import roomescape.domain.dto.TimeResponseDto;
import roomescape.service.TimeManageService;

import java.util.List;

@Controller
public class TimeManageController {

    private TimeManageService timeManageService;

    public TimeManageController(TimeManageService timeManageService) {
        this.timeManageService = timeManageService;
    }

    @GetMapping("/time")
    public String time(){
        return "new-reservation";
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponseDto.Create> createTime(@RequestBody TimeRequestDto.Create request) {
        TimeResponseDto.Create createdTime = timeManageService.createTime(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/times/" + createdTime.getId())
                .body(createdTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponseDto.Get>> getTime() {
        return ResponseEntity.ok(timeManageService.getTime());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable(name = "id") Long id) {

        timeManageService.deleteTime(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
