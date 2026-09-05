package com.cholog.roomescape.roomescape;

import com.cholog.roomescape.domain.entity.Time;
import com.cholog.roomescape.domain.exception.badrequest.TimeNotValidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

public class TimeEntityTest {

    @Test
    @DisplayName("시각이 null이라면, Time을 생성할 수 없다.")
    void timeMustRequiredTime() {
        // given
        LocalTime time = null;

        // then
        Assertions.assertThrows(
                TimeNotValidException.class, () -> {
                    // when
                    new Time(time);
                }
        );
    }
}
