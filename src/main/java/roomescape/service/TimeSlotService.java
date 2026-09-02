package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.TimeSlot;
import roomescape.exception.NotFoundTimeSlotException;
import roomescape.repository.TimeSlotRepository;

import java.util.List;
import java.util.Objects;

@Service
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotService(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    @Transactional(readOnly = true)
    public List<TimeSlot> findAll() {
        return timeSlotRepository.findAll();
    }

    @Transactional
    public TimeSlot save(TimeSlot timeSlot) {
        return timeSlotRepository.save(timeSlot);
    }

    @Transactional
    public void deleteById(Long id) {
        Objects.requireNonNull(id, "id는 null일 수 없습니다.");

        int deleted = timeSlotRepository.deleteById(id);
        if (deleted == 0) {
            throw new NotFoundTimeSlotException("시간을 찾을 수 없습니다. id=" + id);
        }
    }
}
