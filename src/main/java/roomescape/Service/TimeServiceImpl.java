package roomescape.Service;

import org.springframework.stereotype.Service;
import roomescape.DTO.TimeRequestDTO;
import roomescape.DTO.TimeResponseDTO;
import roomescape.Domain.Time;
import roomescape.Repository.TimeRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TimeServiceImpl implements TimeService {
    private final TimeRepository timeRepository;

    public TimeServiceImpl(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @Override
    public List<TimeResponseDTO> findAllTimeReservations() {
        return timeRepository.findAllTimeReservations().stream()
                .map(TimeResponseDTO::from).toList();
    }

    @Override
    public TimeResponseDTO findTimeReservationById(Long id) {
        Time time = timeRepository.findTimeReservationById(id);
        
        if (time == null || time.isEmpty()) {
            throw new NoSuchElementException("찾으려는 아이디가 존재하지 않습니다." + id);
        }
        return TimeResponseDTO.from(time);
    }

    @Override
    public Long createTimeReservation(TimeRequestDTO timeRequest) {
        return timeRepository.createTimeReservation(timeRequest.getTime());
    }

    @Override
    public void deleteTimeReservationById(Long id) {
        timeRepository.deleteTimeReservation(id);
    }
}
