package roomescape.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.advice.ErrorCode;
import roomescape.advice.RoomEscapeException;
import roomescape.model.Time;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeRepository repository;

    public List<Time> getAllTime() {
        return repository.findAll();
    }

    public Time addTime(Time time) {
        Time savedTime = repository.save(time);
        return savedTime;
    }

    public void deleteTime(Long id) {
        if (!repository.exitsById(id)) { // existsById로 이름 수정 권장
            throw new RoomEscapeException(ErrorCode.TIME_NOT_FOUND);
        }
        repository.deleteById(id);
    }

}
