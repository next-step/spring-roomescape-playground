package com.cholog.roomescape.roomescape;

import com.cholog.roomescape.domain.entity.Reservation;
import com.cholog.roomescape.domain.entity.Time;
import com.cholog.roomescape.domain.exception.badrequest.ReservationNotValidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationEntityTest {

    private Time time;

    @BeforeEach
    void setup() {

    }

    @Test
    @DisplayName("이름이 null이라면, Reservation을 생성할 수 없다.")
    void reservationMustRequiredName() {
        // given
        String name = null;

        // then
        Assertions.assertThrows(ReservationNotValidException.class, () -> {
            // when
            new Reservation(null, LocalDate.of(2026, 8, 21), new Time(LocalTime.of(22, 51)));
        });
    }

    @Test
    @DisplayName("날짜가 null이라면, Reservation을 생성할 수 없다.")
    void reservationMustRequiredDate() {
        // given
        LocalDate date = null;

        // then
        Assertions.assertThrows(ReservationNotValidException.class, () -> {
            // when
            new Reservation("Alice", date, new Time(LocalTime.of(22, 51)));
        });
    }

    @Test
    @DisplayName("시각이 null이라면, Reservation을 생성할 수 없다.")
    void reservationMustRequiredTime() {
        // given
        Time time = null;

        Assertions.assertThrows(ReservationNotValidException.class, () -> {
            // when
            new Reservation("Alice", LocalDate.of(2026, 8, 21), time);
        });
    }
}
