package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.exception.BadRequestException;
import roomescape.repository.TimeRepository;

@Controller
public class TimeController {

        private final TimeRepository timeRepository;

        public TimeController(TimeRepository timeRepository) {
            this.timeRepository = timeRepository;
        }

        @GetMapping("/times")
        @ResponseBody
        public List<TimeResponse> showTimes() {
            return timeRepository.findAll().stream()
                    .map(TimeResponse::from)
                    .toList();
        }

        @PostMapping("/times")
        @ResponseBody
        public ResponseEntity<TimeResponse> addTime(@RequestBody @Valid TimeRequest request) {

            Time savedTime = timeRepository.save(request.time());

            return ResponseEntity
                    .created(URI.create("/times/" + savedTime.getId()))
                    .body(TimeResponse.from(savedTime));
        }

        @DeleteMapping("/times/{id}")
        public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
            boolean deleted = timeRepository.deleteById(id);

            if (!deleted) {
                throw new BadRequestException("시간이 존재하지 않습니다.");
            } else {
                return ResponseEntity.noContent().build();
            }
        }
}
