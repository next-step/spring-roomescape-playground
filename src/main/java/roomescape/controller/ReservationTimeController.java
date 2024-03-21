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
import roomescape.service.ReservationTimeService;

import java.util.List;


@RequestMapping("/times")
@Controller
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<TimeDto>> getAllTimes() {
        List<ReservationTime> times = reservationTimeService.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(TimeDto.from(times));
    }

    @PostMapping
    public ResponseEntity<String> createTime(@RequestBody TimeSaveRequestDto timeRequest) {
        long id = reservationTimeService.save(new ReservationTime(timeRequest.time()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/times/" + id)
                .build();
    }

    @DeleteMapping("/{deleteId}")
    public ResponseEntity<Void> deleteTime(@PathVariable(name = "deleteId") long deleteId) {
        reservationTimeService.deleteById(deleteId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
