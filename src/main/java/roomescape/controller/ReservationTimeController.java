package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.controller.dto.TimeDto;
import roomescape.controller.dto.TimeSaveRequestDto;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;


@RequestMapping("/times")
@Controller
public class ReservationTimeController {

    private final ReservationTimeRepository timeRepository;

    public ReservationTimeController(ReservationTimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping
    public ResponseEntity<List<TimeDto>> getAllTimes() {
        List<ReservationTime> times = timeRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(TimeDto.from(times));
    }

    @PostMapping
    public ResponseEntity<String> createTimes(@RequestBody TimeSaveRequestDto timeRequest) {
        long id = timeRepository.save(new ReservationTime(timeRequest.time()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/times/" + id)
                .build();
    }

    @DeleteMapping("/{deleteId}")
    public ResponseEntity<Void> deleteTime(@PathVariable(name = "deleteId") long deleteId) {
        timeRepository.deleteById(deleteId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
