package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
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
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.error.ErrorMessage;
import roomescape.error.exception.InvalidValueException;
import roomescape.repository.ReservationDAO;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationDAO reservationDAO;

    @InjectMocks
    private ReservationService reservationService;

    private ReservationCreateRequest validRequest;

    @BeforeEach
    void setUp() {
        LocalTime fixedTime = LocalTime.now();
        validRequest = new ReservationCreateRequest("파도", LocalDate.now().plusDays(1), fixedTime);
    }

    @Test
    @DisplayName("예약을 정상적으로 생성하는지 확인")
    void 예약을_정상적으로_생성할_수_있다() {
        //given
        LocalTime fixedTime = LocalTime.now();
        Reservation reservation = new Reservation(validRequest.name(), validRequest.date(), validRequest.time());
        when(reservationDAO.createReservation(reservation)).thenReturn(reservation);

        //when
        ReservationResponse response = reservationService.reserve(validRequest);

        //then
        assertAll(
            () -> assertThat(response.id()).isEqualTo(reservation.getId()),
            () -> assertThat(response.name()).isEqualTo("파도"),
            () -> assertThat(response.date()).isEqualTo(LocalDate.now().plusDays(1)),
            () -> assertThat(response.time()).isEqualToIgnoringNanos(fixedTime)
        );

        verify(reservationDAO, times(1)).createReservation(reservation);
    }

    @Test
    @DisplayName("예약 생성 시 유효하지 않은 날짜가 주어지면 예외를 발생시킨다")
    void 예약_생성_시_유효하지_않은_날짜_예외() {
        //given
        ReservationCreateRequest invalidRequest = new ReservationCreateRequest("파도", LocalDate.now().minusDays(1), LocalTime.now().minusHours(1));
        Reservation reservation = new Reservation(invalidRequest.name(), invalidRequest.date(), invalidRequest.time());

        //when & then
        assertThatThrownBy(() -> reservationService.reserve(invalidRequest))
            .isInstanceOf(InvalidValueException.class)
            .hasMessageContaining(ErrorMessage.INVALID_FUTURE_TIME.getMessage());

        verify(reservationDAO, never()).createReservation(reservation);
    }

    @Test
    @DisplayName("예약 생성 시 유효하지 않은 시간이 주어지면 예외를 발생시킨다")
    void 예약_생성_시_유효하지_않은_시간_예외() {
        //given
        ReservationCreateRequest invalidTimeRequest = new ReservationCreateRequest("파도", LocalDate.now(), LocalTime.now().minusMinutes(10));
        Reservation reservation = new Reservation(invalidTimeRequest.name(), invalidTimeRequest.date(),
            invalidTimeRequest.time());

        //when & then
        assertThatThrownBy(() -> reservationService.reserve(invalidTimeRequest))
            .isInstanceOf(InvalidValueException.class)
            .hasMessageContaining(ErrorMessage.INVALID_FUTURE_TIME.getMessage());

        verify(reservationDAO, never()).createReservation(reservation);
    }

    @Test
    @DisplayName("예약 조회 시 모든 예약 목록을 반환한다")
    void 예약_조회() {
        //given
        LocalTime fixedTime = LocalTime.now();
        List<Reservation> mockReservations = List.of(
            new Reservation(1L, "콜리", LocalDate.now().plusDays(1), fixedTime),
            new Reservation(2L, "파도", LocalDate.now().plusDays(2), fixedTime)
        );
        when(reservationDAO.findReservations()).thenReturn(mockReservations);

        //when
        List<Reservation> reservations = reservationService.showReservations();

        //then
        assertThat(reservations).containsExactly(
            new Reservation(1L, "콜리", LocalDate.now().plusDays(1), fixedTime),
            new Reservation(2L, "파도", LocalDate.now().plusDays(2), fixedTime)
        );

        verify(reservationDAO, times(1)).findReservations();
    }
}
