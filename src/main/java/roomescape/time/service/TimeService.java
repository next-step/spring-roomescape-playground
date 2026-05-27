package roomescape.time.service;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;
import roomescape.time.TimeDoesNotExistException;
import roomescape.time.TimeDuplicateException;
import roomescape.time.domain.*;
import roomescape.time.dto.CreateTimeRequest;
import roomescape.time.dto.TimeResponse;

import java.util.List;

@Service
public class TimeService {
    private final Times times;

    public TimeService(Times times) {
        this.times = times;
    }

    public List<TimeResponse> getTimes() {
        return times.getAll().stream()
                .map(TimeResponse::from)
                .toList();
    }

    public TimeResponse createTime(@Nonnull CreateTimeRequest request) {
        CreateTimeInfo info = request.convertToDomain();

        try {
            Time time = times.create(info);
            return TimeResponse.from(time);
        } catch (TimeException.DuplicateTime e) {
            throw new TimeDuplicateException(e);
        }
    }

    public void deleteTime(@Nonnull TimeId id) {
        try {
            times.delete(id);
        } catch (TimeException.DoesNotExist e) {
            throw new TimeDoesNotExistException();
        }
    }
}
