package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.TimeEntity;
import roomescape.dto.TimeDTO;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<TimeDTO> getAllTimes() {
        return timeRepository.findAll().stream()
                .map(TimeEntity::toDTO)
                .toList();
    }

    public TimeDTO addTime(TimeDTO timeDTO) {
        TimeEntity entity = TimeDTO.toEntity(timeDTO);
        TimeEntity savedEntity = timeRepository.save(entity);
        return TimeEntity.toDTO(savedEntity);
    }

    public void deleteTime(Long id) {
        timeRepository.deleteById(id);
    }
}
