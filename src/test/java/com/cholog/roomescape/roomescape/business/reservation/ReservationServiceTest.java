package com.cholog.roomescape.roomescape.business.reservation;

import com.cholog.roomescape.roomescape.dto.request.ReservationRequest;
import com.cholog.roomescape.roomescape.entity.Reservation;
import com.cholog.roomescape.roomescape.entity.Time;
import com.cholog.roomescape.roomescape.exception.badrequest.TimeNotValidException;
import com.cholog.roomescape.roomescape.exception.conflict.ReservationConflictException;
import com.cholog.roomescape.roomescape.exception.notfound.ReservationNotFoundException;
import com.cholog.roomescape.roomescape.exception.notfound.TimeNotFoundException;
import com.cholog.roomescape.roomescape.repository.ReservationRepositoryImpl;
import com.cholog.roomescape.roomescape.repository.TimeRepository;
import com.cholog.roomescape.roomescape.repository.TimeRepositoryImpl;
import com.cholog.roomescape.roomescape.service.ReservationService;
import com.cholog.roomescape.roomescape.service.ReservationServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@Import({ReservationServiceImpl.class, ReservationRepositoryImpl.class, TimeRepositoryImpl.class})
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TimeRepository timeRepository;

    private LocalTime dummyLocalTime = LocalTime.of(10, 0);
    private Time savedTime;

    @BeforeEach
    void setup() {
        savedTime = timeRepository.save(new Time(dummyLocalTime));
    }

    @AfterEach
    public void teardown() {
        this.reservationService = null;
    }

    private String savedTimeId() {
        return savedTime.getId().toString();
    }

    @Test
    @DisplayName("createReservation을 호출하면, 예약 엔터티를 반환한다")
    void createReservationTest() {
        // given
        ReservationRequest request = new ReservationRequest("Alice", LocalDate.now(), savedTimeId());

        // when
        Reservation createdReservation = reservationService.createReservation(request);

        // then
        assertThat(createdReservation.getId()).isNotNull();
        assertThat(createdReservation.getName()).isEqualTo(request.name());
        assertThat(createdReservation.getDate()).isEqualTo(request.date());
        assertThat(createdReservation.getTime().getId().toString()).isEqualTo(request.time());
        assertThat(createdReservation.getTime().getTime()).isEqualTo(dummyLocalTime);
    }

    @Test
    @DisplayName("저장된 적 없는 시각 id로 createReservation을 호출하면 예외가 발생한다.")
    void createReservationWithNotExistingTimeTest() {
        // given
        String notExistingTimeId = String.valueOf(savedTime.getId() + 1L);
        ReservationRequest request = new ReservationRequest("Alice", LocalDate.now(), notExistingTimeId);

        // then
        Assertions.assertThrows(
                TimeNotFoundException.class,

                // when
                () -> reservationService.createReservation(request)
        );
        assertThat(reservationService.findAllReservations().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("숫자가 아닌 시각 id로 createReservation을 호출하면 예외가 발생한다.")
    void createReservationWithNotNumericTimeTest() {
        // given
        ReservationRequest request = new ReservationRequest("Alice", LocalDate.now(), "10:00");

        // then
        Assertions.assertThrows(
                TimeNotValidException.class,

                // when
                () -> reservationService.createReservation(request)
        );
        assertThat(reservationService.findAllReservations().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("동일한 이름, 날짜, 시각으로 createReservation을 두 번 호출하면 예외가 발생한다.")
    void createDuplicatedReservationTest() {
        // given
        ReservationRequest request = new ReservationRequest("Alice", LocalDate.now(), savedTimeId());
        reservationService.createReservation(request);

        // then
        Assertions.assertThrows(
                ReservationConflictException.class,

                // when
                () -> reservationService.createReservation(request)
        );
        assertThat(reservationService.findAllReservations().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("findAllReservations를 호출하면, 저장되어 있는 모든 예약을 반환한다.")
    void findAllReservationsTest() {
        // given
        ReservationRequest request = new ReservationRequest("Alice", LocalDate.now(), savedTimeId());
        Reservation isNotSaved = new Reservation("Bob", LocalDate.now(), savedTime);
        Reservation createdReservation = reservationService.createReservation(request);

        // when
        List<Reservation> reservations = reservationService.findAllReservations();

        // then
        assertThat(reservations.contains(createdReservation)).isTrue();
        assertThat(reservations.contains(isNotSaved)).isFalse();
    }

    @Test
    @DisplayName("deleteReservation()을 호출하면 저장되어 있는 레코드를 삭제한다.")
    void deleteReservationTest() {
        // given
        ReservationRequest request = new ReservationRequest("Alice", LocalDate.now(), savedTimeId());
        Reservation createdReservation = reservationService.createReservation(request);

        // when
        reservationService.deleteReservation(createdReservation.getId());

        // then
        assertThat(reservationService.findAllReservations().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("잘못된 ID로 deleteReservation()을 호출하면 예외가 발생한다.")
    void deleteReservationByIllegalIdTest() {
        // given
        ReservationRequest request = new ReservationRequest("Alice", LocalDate.now(), savedTimeId());
        Reservation createdReservation = reservationService.createReservation(request);

        // then
        Assertions.assertThrows(
                ReservationNotFoundException.class,

                // when
                () -> reservationService
                        .deleteReservation(
                                createdReservation.getId() + 1L
                        )
        );
        assertThat(reservationService.findAllReservations().size()).isEqualTo(1);
    }
}
