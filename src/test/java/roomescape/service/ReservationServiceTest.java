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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.reservation.request.ReservationRequest;
import roomescape.dto.reservation.response.ReservationResponse;
import roomescape.dto.time.response.TimeResponse;
import roomescape.error.ErrorMessage;
import roomescape.error.exception.InvalidValueException;
import roomescape.repository.ReservationDAO;
import roomescape.repository.TimeDAO;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    private ReservationDAO reservationDAO;

    @Mock
    private TimeDAO timeDAO;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void 예약을_정상적으로_생성할_수_있다() {
        // given
        LocalTime reservationTime = LocalTime.of(15, 0);
        LocalDate futureDate = LocalDate.now().plusDays(1);

        Time time = new Time(1L, reservationTime);
        ReservationRequest validRequest = new ReservationRequest("파도", futureDate, time.getId());
        when(timeDAO.findTime(time.getId())).thenReturn(time);

        Reservation reservation = new Reservation(validRequest.name(), validRequest.date(), time);
        when(reservationDAO.createReservation(argThat(newReservation ->
            newReservation.getName().equals(reservation.getName()) &&
                newReservation.getDate().equals(reservation.getDate()) &&
                newReservation.getTime().equals(reservation.getTime())
        ))).thenReturn(reservation);

        // when
        ReservationResponse response = reservationService.reserve(validRequest);

        // then
        assertAll(
            () -> assertThat(response.id()).isEqualTo(reservation.getId()),
            () -> assertThat(response.name()).isEqualTo("파도"),
            () -> assertThat(response.date()).isEqualTo(futureDate),
            () -> assertThat(response.time()).isEqualTo(new TimeResponse(time.getId(), time.getTime()))
        );

        verify(reservationDAO, times(1)).createReservation(any(Reservation.class));
    }

    @Test
    void 예약_생성_시_유효하지_않은_날짜_예외() {
        // given
        LocalTime reservationTime = LocalTime.of(15, 0);
        LocalDate pastDate = LocalDate.now().minusDays(1);

        Time time = new Time(1L, reservationTime);
        ReservationRequest invalidRequest = new ReservationRequest("파도", pastDate, time.getId());
        when(timeDAO.findTime(time.getId())).thenReturn(time);

        // when & then
        assertThatThrownBy(() -> reservationService.reserve(invalidRequest))
            .isInstanceOf(InvalidValueException.class)
            .hasMessageContaining(ErrorMessage.INVALID_FUTURE_TIME.getMessage());

        verify(reservationDAO, never()).createReservation(any(Reservation.class));
    }

    @Test
    void 예약_생성_시_유효하지_않은_시간_예외() {
        // given
        LocalDate validDate = LocalDate.now();
        LocalTime reservationTime = LocalTime.now().minusHours(1);

        Time time = new Time(1L, reservationTime);
        ReservationRequest invalidTimeRequest = new ReservationRequest("파도", validDate, time.getId());
        when(timeDAO.findTime(time.getId())).thenReturn(time);

        // when & then
        assertThatThrownBy(() -> reservationService.reserve(invalidTimeRequest))
            .isInstanceOf(InvalidValueException.class)
            .hasMessageContaining(ErrorMessage.INVALID_FUTURE_TIME.getMessage());

        verify(reservationDAO, never()).createReservation(any(Reservation.class));
    }

    @Test
    void 예약_조회() {
        // given
        LocalTime reservationTime = LocalTime.of(15, 0);
        LocalDate firstDate = LocalDate.now().plusDays(1);
        LocalDate secondDate = LocalDate.now().plusDays(2);

        Time time = new Time(1L, reservationTime);
        List<Reservation> mockReservations = List.of(
            new Reservation(1L, "콜리", firstDate, time),
            new Reservation(2L, "파도", secondDate, time)
        );
        when(reservationDAO.findReservations()).thenReturn(mockReservations);

        // when
        List<ReservationResponse> reservations = reservationService.showReservations();

        // then
        assertThat(reservations).containsExactly(
            new ReservationResponse(1L, "콜리", firstDate, new TimeResponse(time.getId(), time.getTime())),
            new ReservationResponse(2L, "파도", secondDate, new TimeResponse(time.getId(), time.getTime()))
        );

        verify(reservationDAO, times(1)).findReservations();
    }
}
