package roomescape.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Time;
import roomescape.exception.InUseTimeException;
import roomescape.exception.NotFoundTimeException;
import roomescape.repository.TimeRepository;

import java.util.List;
import java.util.Objects;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @Transactional(readOnly = true)
    public List<Time> findAll() {
        return timeRepository.findAll();
    }

    @Transactional
    public Time save(Time time) {
        return timeRepository.save(time);
    }

    @Transactional
    public void deleteById(Long id) {
        Objects.requireNonNull(id, "id는 null일 수 없습니다.");

        int deleted;
        try {
            deleted = timeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new InUseTimeException("예약에서 사용 중인 시간은 삭제할 수 없습니다. id=" + id);
        }

        if (deleted == 0) {
            throw new NotFoundTimeException("시간을 찾을 수 없습니다. id=" + id);
        }
    }
}
