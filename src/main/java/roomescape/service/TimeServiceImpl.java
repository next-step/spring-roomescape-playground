package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.repository.JdbcTemplateTimeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeServiceImpl implements TimeService {
    private final JdbcTemplateTimeRepository jdbcTemplateTimeRepository;

    public List<Time> getAllTime() {return jdbcTemplateTimeRepository.findAllTime(); }

    public Time addTime(Time time) {
        Time newTime = jdbcTemplateTimeRepository.insertTime(time);
        return newTime;
    }

    public void deleteTime(Long id) {
        jdbcTemplateTimeRepository.deleteTime(id);
    }

}
