package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import roomescape.domain.Time;
import roomescape.repository.TimeRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimeService {

    private final TimeRepository timeRepository;

    public List<Time> times() {
        return timeRepository.findAll();
    }

    @Transactional
    public Time addTime(Time request) {
        return timeRepository.save(request);
    }

    @Transactional
    public void deleteTime(Long id) {
        timeRepository.deleteById(id);
    }
}
