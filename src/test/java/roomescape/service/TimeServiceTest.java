package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.domain.Time;
import roomescape.dto.time.request.TimeRequest;
import roomescape.dto.time.response.TimeResponse;
import roomescape.error.ErrorMessage;
import roomescape.error.exception.InvalidValueException;
import roomescape.repository.TimeDAO;

@ExtendWith(MockitoExtension.class)
public class TimeServiceTest {
    @Mock
    private TimeDAO timeDAO;

    @InjectMocks
    private TimeService timeService;

    @Test
    void 시간을_정상적으로_생성할_수_있다() {
        // given
        Long reservationTimeId = 1L;
        LocalTime reservationTime = LocalTime.of(15, 0);
        Time time = new Time(reservationTimeId, reservationTime);
        TimeRequest validRequest = new TimeRequest(time.getTime());
        when(timeDAO.createTime(any(Time.class))).thenReturn(time);

        // when
        TimeResponse response = timeService.createTime(validRequest);

        // then
        assertAll(
            () -> assertThat(response.id()).isEqualTo(time.getId()),
            () -> assertThat(response.time()).isEqualTo(time.getTime())
        );

        verify(timeDAO, times(1)).createTime(any(Time.class));
    }

    @Test
    void 중복된_시간_예외() {
        // given
        LocalTime reservationTime = LocalTime.of(15, 0);
        TimeRequest validRequest = new TimeRequest(reservationTime);
        when(timeDAO.existsTime(validRequest.time())).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> timeService.createTime(validRequest))
            .isInstanceOf(InvalidValueException.class)
            .hasMessage(ErrorMessage.DUPLICATE_TIME.getMessage());
    }

    @Test
    void 시간을_모두_조회할_수_있다() {
        // given
        Long firstReservationTimeId = 1L;
        LocalTime firstReservationTime = LocalTime.of(15, 0);
        Time firstTime = new Time(firstReservationTimeId, firstReservationTime);

        Long secondReservationTimeId = 2L;
        LocalTime secondReservationTimeValue = LocalTime.of(10, 0);
        Time secondTime = new Time(secondReservationTimeId, secondReservationTimeValue);

        List<Time> mockTimes = List.of(firstTime, secondTime);
        when(timeDAO.findTimes()).thenReturn(mockTimes);

        // when
        List<TimeResponse> responses = timeService.findTimes();

        // then
        assertAll(
            () -> assertThat(responses).hasSize(2),
            () -> {
                assertThat(responses.get(0).id()).isEqualTo(firstReservationTimeId);
                assertThat(responses.get(0).time()).isEqualTo(firstTime.getTime());
            },
            () -> {
                assertThat(responses.get(1).id()).isEqualTo(secondReservationTimeId);
                assertThat(responses.get(1).time()).isEqualTo(secondTime.getTime());
            }
        );

        verify(timeDAO, times(1)).findTimes();
    }

    @Test
    void 시간_삭제_테스트() {
        // given
        Long reservationTimeId = 1L;
        LocalTime reservationTime = LocalTime.of(15, 0);
        Time time = new Time(reservationTimeId, reservationTime);
        when(timeDAO.findTime(reservationTimeId)).thenReturn(time);

        // when
        timeService.deleteTime(reservationTimeId);

        // then
        verify(timeDAO, times(1)).deleteTime(reservationTimeId);
        when(timeDAO.findTime(reservationTimeId)).thenThrow(new InvalidValueException(ErrorMessage.NO_TIME.getMessage()));
        assertThatThrownBy(() -> timeService.findTimeById(reservationTimeId))
            .isInstanceOf(InvalidValueException.class)
            .hasMessage(ErrorMessage.NO_TIME.getMessage());
    }
}
