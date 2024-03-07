package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.TimeRequestDto;
import roomescape.dto.TimeResponseDto;
import roomescape.exception.NoParameterException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeDao timeDao;

    public List<TimeResponseDto> loadTimeList(){
        return timeDao.findAll().stream()
                .map(TimeResponseDto::from)
                .collect(Collectors.toList());
    }

    public TimeResponseDto createTime(TimeRequestDto timeRequest){
        if (timeRequest.time() == null) {
            throw new NoParameterException("Time Have No Parameter");
        }
        Time time = new Time(null, timeRequest.time());
        return TimeResponseDto.from(timeDao.insert(time));
    }

    public void deleteTime (Long id){
        timeDao.delete(id);
    }
}
