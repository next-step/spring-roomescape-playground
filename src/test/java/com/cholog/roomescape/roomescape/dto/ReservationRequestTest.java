package com.cholog.roomescape.roomescape.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.cholog.roomescape.roomescape.dto.request.ReservationRequest;

import java.time.LocalDate;
import java.time.LocalTime;
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
    void nameMustRequiredIncreateReservationRequest() {

        // given
        String name = "";
        LocalDate reservedDate = LocalDate.now();
        LocalTime reservedTime = LocalTime.now();

        ReservationRequest request = new ReservationRequest(
                name, reservedDate, reservedTime
        );

       // when
        Set<ConstraintViolation<ReservationRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("이름 필드는 최대 20자를 초과할 수 없다")
    void nameCannotExceed20CharIncreateReservationRequest() {

        // given
        String name = "abcdefghijklmnopqrstu";
        LocalDate reservedDate = LocalDate.now();
        LocalTime reservedTime = LocalTime.now();

        ReservationRequest request = new ReservationRequest(
                name, reservedDate, reservedTime
        );

        // when
        Set<ConstraintViolation<ReservationRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("이름 필드는 null값을 허용하지 않는다")
    void nameMustNotBeNullIncreateReservationRequest() {

        // given
        LocalDate reservedDate = LocalDate.now();
        LocalTime reservedTime = LocalTime.now();

        ReservationRequest request = new ReservationRequest(
                null, reservedDate, reservedTime
        );

        // when
        Set<ConstraintViolation<ReservationRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("예약 날짜 필드는 null값을 허용하지 않는다")
    void dateMustNotBeNullIncreateReservationRequest() {

        // given
        String name = "Alice";
        LocalDate reservedDate = null;
        LocalTime reservedTime = LocalTime.now();

        ReservationRequest request = new ReservationRequest(
                name, reservedDate, reservedTime
        );

        // when
        Set<ConstraintViolation<ReservationRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("예약 시각 필드는 null값을 허용하지 않는다")
    void timeMustNotBeNullIncreateReservationRequest() {

        // given
        String name = "Alice";
        LocalDate reservedDate = LocalDate.now();
        LocalTime reservedTime = null;

        ReservationRequest request = new ReservationRequest(
                name, reservedDate, reservedTime
        );

        // when
        Set<ConstraintViolation<ReservationRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
    }

    @Test
    @DisplayName("예약을 정상적으로 처리한 경우")
    void allValidatedIncreateReservationRequest() {

        // given
        String name = "Alice";
        LocalDate reservedDate = LocalDate.now();
        LocalTime reservedTime = LocalTime.now();

        ReservationRequest request = new ReservationRequest(
                name, reservedDate, reservedTime
        );

        // when
        Set<ConstraintViolation<ReservationRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

}
