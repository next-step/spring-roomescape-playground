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
                .map(this::entityToDTO)
                .toList();
    }

    public TimeDTO addTime(TimeDTO timeDTO) {
        TimeEntity entity = dtoToEntity(timeDTO);
        TimeEntity savedEntity = timeRepository.save(entity);
        return entityToDTO(savedEntity);
    }

    public void deleteTime(Long id) {
        timeRepository.deleteById(id);
    }

    private TimeDTO entityToDTO(TimeEntity entity) {
        return new TimeDTO(entity.id(), entity.time());
    }

    private TimeEntity dtoToEntity(TimeDTO dto) {
        return new TimeEntity(dto.id(), dto.time());
    }
}
