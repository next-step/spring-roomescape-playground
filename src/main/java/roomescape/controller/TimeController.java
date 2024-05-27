package roomescape.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.TimeRequestDTO;
import roomescape.model.TimeResponseDTO;
import roomescape.repository.service.TimeService;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("times")
public class TimeController {

    private final TimeService timeService;

    @ResponseBody
    @GetMapping
    public List<TimeResponseDTO> findTime() {
        List<TimeResponseDTO> times = timeService.findAll();
        return times;
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<TimeResponseDTO> timeAdd(@RequestBody TimeRequestDTO timeRequestDTO) {
        TimeResponseDTO resultTime = timeService.timeAdd(timeRequestDTO);
        HttpHeaders headers = new HttpHeaders();
        String uri = "/times/" + resultTime.getId();
        headers.setLocation(URI.create(uri));
        ResponseEntity<TimeResponseDTO> response = new ResponseEntity<>(resultTime, headers, HttpStatus.CREATED);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteController(@PathVariable int id) {
        timeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
