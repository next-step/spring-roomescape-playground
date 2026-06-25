package roomescape.time.model;

import org.junit.jupiter.api.Test;
import roomescape.common.exception.AfterCloseException;
import roomescape.common.exception.BeforeOpenException;
import roomescape.common.exception.OverlappingReservationTimeException;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimeManagerTest {

    @Test
    void throwsExceptionWhenTimeIsBeforeOpen() {
        TimeManager timeManager = new TimeManager(List.of());

        assertThatThrownBy(() -> timeManager.validateTime(createTime("08:59")))
                .isInstanceOf(BeforeOpenException.class)
                .hasMessage("Time should be after 09:00");
    }

    @Test
    void throwsExceptionWhenTimeEndsAfterClose() {
        TimeManager timeManager = new TimeManager(List.of());

        assertThatThrownBy(() -> timeManager.validateTime(createTime("20:01")))
                .isInstanceOf(AfterCloseException.class)
                .hasMessage("Time should be before 22:00");
    }

    @Test
    void throwsExceptionWhenTimeOverlapsExistingTime() {
        TimeManager timeManager = new TimeManager(List.of(createTime("10:00")));

        assertThatThrownBy(() -> timeManager.validateTime(createTime("11:00")))
                .isInstanceOf(OverlappingReservationTimeException.class)
                .hasMessage("Time is overlapping");
    }

    @Test
    void doesNotThrowExceptionWhenTimeDoesNotOverlapExistingTime() {
        TimeManager timeManager = new TimeManager(List.of(createTime("10:00")));

        assertThatCode(() -> timeManager.validateTime(createTime("12:00")))
                .doesNotThrowAnyException();
    }

    private Time createTime(String time) {
        return new Time(LocalTime.parse(time));
    }
}