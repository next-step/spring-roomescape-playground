package com.cholog.roomescape.domain.service;

import com.cholog.roomescape.domain.entity.Time;
import com.cholog.roomescape.domain.exception.notfound.TimeNotFoundException;
import com.cholog.roomescape.domain.repository.TimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
public class TimeServiceImpl implements TimeService {

    private final TimeRepository timeRepository;

    public TimeServiceImpl(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @Override
    public List<Time> findAllTime() {
        return timeRepository.findAll();
    }

    @Transactional
    @Override
    public Time createTime(LocalTime time) {
        return timeRepository.save(new Time(time));
    }

    @Transactional
    @Override
    public void deleteTime(Long timeId) {
        Time time = timeRepository.findById(timeId)
                .orElseThrow(TimeNotFoundException::new);

        timeRepository.delete(time);
    }
}
