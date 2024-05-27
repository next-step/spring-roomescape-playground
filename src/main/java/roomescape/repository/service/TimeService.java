package roomescape.repository.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import roomescape.model.*;
import roomescape.repository.TimeRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeRepository timeRepository;

    public List<TimeResponseDTO> findAll() {
        List<Time> times = timeRepository.findAll();
        List<TimeResponseDTO> result = times.stream()
                .map(TimeResponseDTO::MakingResponse).toList();
        return result;
    }

    public TimeResponseDTO timeAdd(TimeRequestDTO timeRequestDTO) {
        Time time = new Time(timeRequestDTO.getTime());
        Time result = timeRepository.timeAdd(time);
        TimeResponseDTO responseDTO = new TimeResponseDTO();
        responseDTO.setId(result.getId());
        responseDTO.setTime(result.getTime());
        return responseDTO;
    }

    public void delete(int id) {
        timeRepository.delete(id);
    }
}
