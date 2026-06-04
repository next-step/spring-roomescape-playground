package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.exception.BadRequestException;
import roomescape.repository.TimeRepository;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<Time> findAll() {
        return timeRepository.findAll();
    }

    public Time create(LocalTime time) {
        return timeRepository.save(time);
    }

    public void delete(Long id) {
        try {
            boolean deleted = timeRepository.deleteById(id);

            if (!deleted) {
                throw new BadRequestException("시간번호가 " + id + "인 시간은 존재하지 않습니다.");
            }
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("예약이 존재하는 시간은 삭제할 수 없습니다.");
        }
    }
}
