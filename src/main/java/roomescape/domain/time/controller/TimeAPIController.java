package roomescape.domain.time.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import roomescape.domain.time.domain.Time;
import roomescape.domain.time.dto.TimeCreateDTO;
import roomescape.domain.time.dto.TimeResponseDTO;
import roomescape.domain.time.mapper.TimeMapper;
import roomescape.domain.time.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeAPIController {

    private final TimeService timeService;

    public TimeAPIController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<TimeResponseDTO> addTime(@RequestBody @Valid TimeCreateDTO timeCreateDTO) {
        Time time = TimeMapper.toEntity(timeCreateDTO);
        Time newTime = timeService.createTime(time);
        TimeResponseDTO timeResponseDTO = TimeMapper.toTimeResponseDTO(newTime);
        return ResponseEntity.created(URI.create("/times/" + timeResponseDTO.getId())).body(timeResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<TimeResponseDTO>> getAllTime() {
        List<TimeResponseDTO> timeResponseDTOList = timeService.getAllTime().stream()
            .map(TimeMapper::toTimeResponseDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(timeResponseDTOList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }

}
