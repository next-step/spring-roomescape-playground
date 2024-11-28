package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.TimeRequestDto;
import roomescape.dto.TimeResponseDto;
import roomescape.service.TimeService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    // 홈화면
    @GetMapping("/time")
    public String reservationPage() {
        return "time";
    }

    @PostMapping("/times") //시간 추가
    public ResponseEntity<TimeResponseDto> createTime(@RequestBody TimeRequestDto requestDto){
        TimeResponseDto newTime = timeService.createTime(requestDto);
        URI location = URI.create("/times/" + newTime.getId());

        return ResponseEntity.created(location)
                .body(newTime);
    }
    @GetMapping("/times") //시간 조회
    public ResponseEntity<List<TimeResponseDto>> findTimes(){
        List<TimeResponseDto> timeList = timeService.findAllTimes();
        return ResponseEntity.ok(timeList);
    }

    @DeleteMapping("/times/{id}") // 시간 삭제
    public ResponseEntity<TimeResponseDto> deleteTime(@PathVariable Long id){
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
