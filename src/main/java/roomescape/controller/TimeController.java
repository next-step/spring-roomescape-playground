package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.TimeDTO;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
public class TimeController {

    @Autowired
    private TimeDao timeDao;

    public TimeController(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @GetMapping("/time")
    public String time() {
        return "time";
    }

    @GetMapping("/times")
    @ResponseBody
    public ResponseEntity<List<TimeDTO>> read() {
        List<Time> times = timeDao.getAllTimes();
        List<TimeDTO> timeDTOS = times.stream()
                .map(this::convertToTimeDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(timeDTOS);
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<TimeDTO> create(@RequestBody Time time) {
        Time newTime = timeDao.insertTime(time);
        TimeDTO timeDTO = convertToTimeDTO(newTime);

        return ResponseEntity.created(URI.create("/times/" + timeDTO.getId())).body(timeDTO);
    }

    @DeleteMapping("/times/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int removed = timeDao.deleteTime(id);
        if (removed == 0) {
            throw new NoSuchElementException("삭제할 항목이 없습니다.");
        }
        return ResponseEntity.noContent().build();
    }

    private TimeDTO convertToTimeDTO(Time time) {
        return new TimeDTO(time.getId(), time.getTime());
    }
}
