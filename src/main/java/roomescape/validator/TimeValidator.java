package roomescape.validator;

import org.springframework.stereotype.Component;
import roomescape.domain.Time;
import roomescape.exception.InvalidReservationException;
import roomescape.repository.TimeRepository;

@Component
public class TimeValidator {

    private final TimeRepository timeRepository;

    public TimeValidator(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public void validateDuplicate(Time time) {
        Integer count = timeRepository.countByTime(java.sql.Time.valueOf(time.getTime()));
        if (count != null && count > 0) {
            throw new InvalidReservationException("이미 존재하는 예약 시간입니다.");
        }
    }
}
