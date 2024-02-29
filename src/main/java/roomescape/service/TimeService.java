package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.TimeRequestDto;
import roomescape.dto.TimeResponseDto;
import roomescape.exception.NoParameterException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeDao timeDao;

    public List<TimeResponseDto> loadTimeList(){
        return timeDao.findAll();
    }

    public TimeResponseDto createTime(TimeRequestDto timeRequest){
        if (StringUtils.isEmpty(timeRequest.time())) {
            throw new NoParameterException("Time Have No Parameter");
        }
        return timeDao.insert(timeRequest);
    }

    public void deleteTime (Long id){
        timeDao.delete(id);
    }
}
