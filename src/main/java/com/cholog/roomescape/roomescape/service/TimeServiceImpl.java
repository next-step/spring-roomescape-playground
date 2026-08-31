package com.cholog.roomescape.roomescape.service;

import com.cholog.roomescape.roomescape.entity.Time;
import com.cholog.roomescape.roomescape.exception.TimeNotFoundException;
import com.cholog.roomescape.roomescape.repository.TimeRepository;
import org.springframework.stereotype.Service;

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

    @Override
    public Time createTime(LocalTime time) {
        return timeRepository.save(new Time(time));
    }

    @Override
    public void deleteTime(Long timeId) {
        Time time = timeRepository.findById(timeId)
                .orElseThrow(TimeNotFoundException::new);

        timeRepository.delete(time);
    }
}
