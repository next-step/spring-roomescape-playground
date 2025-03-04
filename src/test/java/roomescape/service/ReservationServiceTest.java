package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Time;
import roomescape.dto.request.CreateReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.global.exception.BadRequestException;
import roomescape.global.exception.ExceptionMessage;
import roomescape.repository.TimeRepository;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
class ReservationServiceTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(Instant.parse("2025-02-17T10:00:00Z"), ZoneId.of("UTC"));

    @SpyBean
    private Clock clock;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TimeRepository timeRepository;

    @BeforeEach
    void setUpClock() {
        doReturn(FIXED_CLOCK.instant()).when(clock).instant();
        doReturn(FIXED_CLOCK.getZone()).when(clock).getZone();
    }

    @Test
    void 예약을_생성할_수_있다() {
        // given
        final Time time = saveTime(LocalTime.of(13, 0));
        final CreateReservationRequest request = createReservationRequest("김철수", 2025, 2, 18, time.getId());
        // when
        final ReservationResponse response = reservationService.createReservation(request);
        // then
        assertAll(
                () -> assertThat(response.name()).isEqualTo(request.name()),
                () -> assertThat(response.date()).isEqualTo(request.date()),
                () -> assertThat(response.time().id()).isEqualTo(request.timeId())
        );
    }

    @Test
    void 동시간대에_예약이_존재한다면_예외가_발생한다() {
        // given
        final Time time = saveTime(LocalTime.of(13, 0));
        final CreateReservationRequest request1 = createReservationRequest("김철수", 2025, 2, 18, time.getId());
        reservationService.createReservation(request1);
        final CreateReservationRequest request2 = createReservationRequest("김영희", 2025, 2, 18, time.getId());
        // when & then
        assertThatThrownBy(() -> reservationService.createReservation(request2))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(
                        ExceptionMessage.RESERVATION_ALREADY_EXISTS.getMessage());
    }

    @Test
    void 현재_시간_이전의_예약_생성_시_예외가_발생한다() {
        // given
        final Time time = saveTime(LocalTime.of(9, 0));
        final CreateReservationRequest request = createReservationRequest("김철수", 2025, 2, 17, time.getId());
        // when & then
        assertThatThrownBy(() -> reservationService.createReservation(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(
                        ExceptionMessage.INVALID_DATETIME.getMessage());
    }

    @Test
    void 총_예약건수를_조회할_수_있다() {
        // given
        final Time time1 = saveTime(LocalTime.of(13, 0));
        final CreateReservationRequest request1 = createReservationRequest("김철수", 2025, 2, 18, time1.getId());
        final Time time2 = saveTime(LocalTime.of(14, 0));
        final CreateReservationRequest request2 = createReservationRequest("김영희", 2025, 2, 18, time2.getId());
        reservationService.createReservation(request1);
        reservationService.createReservation(request2);
        // when
        final List<ReservationResponse> reservationResponses = reservationService.getReservations();
        // then
        assertThat(reservationResponses).hasSize(2);
    }

    @Test
    void 예약을_삭제할_수_있다() {
        // given
        final Time time = saveTime(LocalTime.of(13, 0));
        final CreateReservationRequest request = createReservationRequest("김철수", 2025, 2, 18, time.getId());
        final ReservationResponse response = reservationService.createReservation(request);
        final int initialReservationSize = reservationService.getReservations().size();
        // when
        reservationService.deleteReservation(response.id());
        // then
        assertThat(reservationService.getReservations()).hasSize(initialReservationSize - 1);
    }

    @Test
    void 존재하지_않는_예약을_삭제하는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> reservationService.deleteReservation(1))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(
                        ExceptionMessage.RESERVATION_NOT_EXISTS.getMessage());
    }

    private Time saveTime(LocalTime time) {
        final Time newTime = new Time(time);
        return timeRepository.save(newTime);
    }

    private CreateReservationRequest createReservationRequest(String name,
                                                              int year, int month, int dayOfMonth,
                                                              long time) {
        return new CreateReservationRequest(
                name,
                LocalDate.of(year, month, dayOfMonth),
                time);
    }
}
