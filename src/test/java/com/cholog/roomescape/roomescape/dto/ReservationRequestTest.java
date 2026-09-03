package com.cholog.roomescape.roomescape.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.cholog.roomescape.roomescape.dto.request.ReservationRequest;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("이름 필드는 비어있을 수 없다")
    void nameMustRequiredInCreateReservationRequest() {

        // given
        String name = "";
        LocalDate reservedDate = LocalDate.now();
        String reservedTimeId = "1";

        ReservationRequest request = new ReservationRequest(
                name, reservedDate, reservedTimeId
        );

       // when
        Set<ConstraintViolation<ReservationRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("이름 필드는 최대 20자를 초과할 수 없다")
    void nameCannotExceed20CharInCreateReservationRequest() {

        // given
        String name = "abcdefghijklmnopqrstu";
        LocalDate reservedDate = LocalDate.now();
        String reservedTimeId = "1";

        ReservationRequest request = new ReservationRequest(
                name, reservedDate, reservedTimeId
        );

        // when
        Set<ConstraintViolation<ReservationRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("이름 필드는 null값을 허용하지 않는다")
    void nameMustNotBeNullInCreateReservationRequest() {

        // given
        LocalDate reservedDate = LocalDate.now();
        String reservedTimeId = "1";

        ReservationRequest request = new ReservationRequest(
                null, reservedDate, reservedTimeId
        );

        // when
        Set<ConstraintViolation<ReservationRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("예약 날짜 필드는 null값을 허용하지 않는다")
    void dateMustNotBeNullInCreateReservationRequest() {

        // given
        String name = "Alice";
        LocalDate reservedDate = null;
        String reservedTimeId = "1";

        ReservationRequest request = new ReservationRequest(
                name, reservedDate, reservedTimeId
        );

        // when
        Set<ConstraintViolation<ReservationRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("예약 시각 필드는 null값을 허용하지 않는다")
    void timeMustNotBeNullInCreateReservationRequest() {

        // given
        String name = "Alice";
        LocalDate reservedDate = LocalDate.now();
        String reservedTimeId = null;

        ReservationRequest request = new ReservationRequest(
                name, reservedDate, reservedTimeId
        );

        // when
        Set<ConstraintViolation<ReservationRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("예약 시각 필드는 빈 문자열을 허용하지 않는다")
    void timeMustNotBeBlankInCreateReservationRequest() {

        // given
        String name = "Alice";
        LocalDate reservedDate = LocalDate.now();
        String reservedTimeId = "";

        ReservationRequest request = new ReservationRequest(
                name, reservedDate, reservedTimeId
        );

        // when
        Set<ConstraintViolation<ReservationRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("예약을 정상적으로 처리한 경우")
    void allValidatedInCreateReservationRequest() {

        // given
        String name = "Alice";
        LocalDate reservedDate = LocalDate.now();
        String reservedTimeId = "1";

        ReservationRequest request = new ReservationRequest(
                name, reservedDate, reservedTimeId
        );

        // when
        Set<ConstraintViolation<ReservationRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

}
