package roomescape.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeRequestDto;
import roomescape.dto.TimeResponseDto;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/times")
@RequiredArgsConstructor
public class TimeController {

    private final TimeService timeService;

    @GetMapping
    public ResponseEntity<List<TimeResponseDto>> LoadTime(){
        return ResponseEntity.ok(timeService.loadTimeList());
    }

    @PostMapping
    public ResponseEntity<TimeResponseDto> CreateTime(@RequestBody TimeRequestDto timeRequest){
        TimeResponseDto timeResponse = timeService.createTime(timeRequest);
        return ResponseEntity.created(URI.create("/times/" + timeResponse.id())).body(timeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteTime(@PathVariable Long id){
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
