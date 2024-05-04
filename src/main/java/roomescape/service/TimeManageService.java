package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.TimeQueryingDAO;
import roomescape.domain.Time;
import roomescape.domain.dto.TimeRequestDto;
import roomescape.domain.dto.TimeResponseDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeManageService {

    private TimeQueryingDAO timeDAO;

    public TimeManageService(TimeQueryingDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    public TimeResponseDto.Create createTime(TimeRequestDto.Create request) {

        Time time = timeDAO.insertTime(request.getTime());

        return new TimeResponseDto.Create(time.getId(), time.getTime());
    }

    public List<TimeResponseDto.Get> getTime() {

        List<Time> timeList = timeDAO.findAllTime();

        List<TimeResponseDto.Get> resultList = new ArrayList<>();

        for(Time time:timeList) {
            resultList.add(new TimeResponseDto.Get(time.getId(), time.getTime()));
        }

        return resultList;
    }

    public void deleteTime(Long id) {

        timeDAO.deleteTime(id);
    }
}
