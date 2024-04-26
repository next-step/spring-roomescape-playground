package roomescape.time.business_layer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.time.domain_model_layer.TimeEntity;
import roomescape.time.data_access_layer.TimeDAO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeDAO timeDB;
    public List<TimeEntity> getTimes() {
        return timeDB.getTimeByDB();
    }

    // getter
    public TimeEntity getTime(Long id) {
        return timeDB.getTimeByDB(id);
    }

    public TimeEntity saveTime(String time) {
        TimeEntity generade_time = generateTime(time);
        return timeDB.saveTimeDB(generade_time);
    }

    private TimeEntity generateTime(String time) {
        return TimeEntity.builder()
                .time(time)
                .build();
    }

    public void deleteTime(Long id) {
        timeDB.deleteTimeDB(id);
    }

}
