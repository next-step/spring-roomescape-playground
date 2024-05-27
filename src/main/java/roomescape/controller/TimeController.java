package roomescape.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.ReservationTime;
import roomescape.repository.TimeRepository;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("times")
public class TimeController {

    private final TimeRepository timeRepository;

    @ResponseBody
    @GetMapping
    public List<ReservationTime> findTime() {
        List<ReservationTime> times = timeRepository.findAll();
        log.info("times = {}", times);
        return times;
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<ReservationTime> timeAdd(@RequestBody ReservationTime time) {
        ReservationTime resultTime = timeRepository.timeAdd(time);
        HttpHeaders headers = new HttpHeaders();
        String uri = "/times/" + resultTime.getId();
        headers.setLocation(URI.create(uri));
        ResponseEntity<ReservationTime> response = new ResponseEntity<>(resultTime, headers, HttpStatus.CREATED);
        log.info("response = {}", response);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteController(@PathVariable int id) {
        timeRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
