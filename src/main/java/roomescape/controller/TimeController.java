package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.dto.TimeRequestDto;
import roomescape.exception.NotFoundTimeException;
import roomescape.exception.ValidateTimeDTO;
import roomescape.repository.TimeRepository;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/times")
public class TimeController {

    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping
    public ResponseEntity<List<TimeRequestDto>> readTimes() {
        List<TimeRequestDto> timeDtos = timeRepository.findAllTimes().stream()
                .map(TimeRequestDto::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(timeDtos);
    }

    @PostMapping
    public ResponseEntity<Time> createTime(@RequestBody TimeRequestDto timeDTO) {
        ValidateTimeDTO.validateTime(timeDTO);
        Long newId = timeRepository.insertTimeId(timeDTO);
        return ResponseEntity.created(URI.create("/times/" + newId)).body(timeDTO.toEntity(newId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        Time time = timeRepository.findTimeById(id)
                .orElseThrow(() -> new NotFoundTimeException("Time with id " + id + " not found"));
        timeRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
