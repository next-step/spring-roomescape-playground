package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.repository.TimeDB;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeDB timeDB;
    public List<Time> getTimes() {
        return timeDB.getTimeByDB();
    }

    public Time getTime(Long id) {
        return timeDB.getTimeByDB(id);
    }

    public Time saveTime(String time) {
        Time generade_time = generateTime(time);
        return timeDB.saveTimeDB(generade_time);
    }

    private Time generateTime(String time) {
        return Time.builder()
                .time(time)
                .build();
    }

    public void deleteTime(Long id) {
        timeDB.deleteTimeDB(id);
    }

}
