package com.cholog.roomescape.roomescape.dto;

import com.cholog.roomescape.domain.dto.request.TimeRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeRequestTest {

    private Validator validator;

    private LocalTime dummyTime = LocalTime.of(10, 0);

    @BeforeEach
    void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("시간 필드는 비어있을 수 없다.")
    void timeMustRequiredInCreateTimeRequest() {
        // given
        LocalTime time = null;

        TimeRequest request = new TimeRequest(time);

        // when
        Set<ConstraintViolation<TimeRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("시간 설정을 정상적으로 처리한 경우")
    void allValidatedInCreateTime() {
        // given
        TimeRequest request = new TimeRequest(dummyTime);

        // when
        Set<ConstraintViolation<TimeRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }
}
