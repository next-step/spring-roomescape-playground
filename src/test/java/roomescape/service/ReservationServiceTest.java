package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.reservation.request.ReservationRequest;
import roomescape.dto.reservation.response.ReservationResponse;
import roomescape.error.ErrorMessage;
import roomescape.error.exception.InvalidValueException;
import roomescape.repository.ReservationDAO;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationDAO reservationDAO;

    @Mock
    private TimeService timeService;

    @InjectMocks
    private ReservationService reservationService;

    private ReservationRequest validRequest;
    private Time time;

    @BeforeEach
    void setUp() {
        time = new Time(1L, LocalTime.now());
        validRequest = new ReservationRequest("파도", LocalDate.now().plusDays(1), time.getId());
    }

    @Test
    @DisplayName("예약을 정상적으로 생성하는지 확인")
    void 예약을_정상적으로_생성할_수_있다() {
        //given
        when(timeService.findTimeById(time.getId())).thenReturn(time);
        Reservation reservation = new Reservation(validRequest.name(), validRequest.date(), time);
        when(reservationDAO.createReservation(argThat(r ->
            r.getName().equals(reservation.getName()) &&
                r.getDate().equals(reservation.getDate()) &&
                r.getTime().equals(reservation.getTime())
        ))).thenReturn(reservation);

        // when
        ReservationResponse response = reservationService.reserve(validRequest);

        // then
        assertAll(
            () -> assertThat(response.id()).isEqualTo(reservation.getId()),
            () -> assertThat(response.name()).isEqualTo("파도"),
            () -> assertThat(response.date()).isEqualTo(LocalDate.now().plusDays(1)),
            () -> assertThat(response.time()).isEqualTo(time)
        );

        verify(reservationDAO, times(1)).createReservation(any(Reservation.class));
    }

    @Test
    @DisplayName("예약 생성 시 유효하지 않은 날짜가 주어지면 예외를 발생시킨다")
    void 예약_생성_시_유효하지_않은_날짜_예외() {
        // given
        ReservationRequest invalidRequest = new ReservationRequest("파도", LocalDate.now().minusDays(1), time.getId());
        when(timeService.findTimeById(time.getId())).thenReturn(time);

        // when & then
        assertThatThrownBy(() -> reservationService.reserve(invalidRequest))
            .isInstanceOf(InvalidValueException.class)
            .hasMessageContaining(ErrorMessage.INVALID_FUTURE_TIME.getMessage());

        verify(reservationDAO, never()).createReservation(any(Reservation.class));
    }

    @Test
    @DisplayName("예약 생성 시 유효하지 않은 시간이 주어지면 예외를 발생시킨다")
    void 예약_생성_시_유효하지_않은_시간_예외() {
        // given
        ReservationRequest invalidTimeRequest = new ReservationRequest("파도", LocalDate.now(), time.getId());
        when(timeService.findTimeById(time.getId())).thenReturn(time);

        // when & then
        assertThatThrownBy(() -> reservationService.reserve(invalidTimeRequest))
            .isInstanceOf(InvalidValueException.class)
            .hasMessageContaining(ErrorMessage.INVALID_FUTURE_TIME.getMessage());

        verify(reservationDAO, never()).createReservation(any(Reservation.class));
    }

    @Test
    @DisplayName("예약 조회 시 모든 예약 목록을 반환한다")
    void 예약_조회() {
        // given
        List<Reservation> mockReservations = List.of(
            new Reservation(1L, "콜리", LocalDate.now().plusDays(1), time),
            new Reservation(2L, "파도", LocalDate.now().plusDays(2), time)
        );
        when(reservationDAO.findReservations()).thenReturn(mockReservations);

        // when
        List<ReservationResponse> reservations = reservationService.showReservations();

        // then
        assertThat(reservations).containsExactly(
            new ReservationResponse(1L, "콜리", LocalDate.now().plusDays(1), time),
            new ReservationResponse(2L, "파도", LocalDate.now().plusDays(2), time)
        );

        verify(reservationDAO, times(1)).findReservations();
    }
}
