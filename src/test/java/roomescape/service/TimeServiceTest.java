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

    private TimeRequest validRequest;
    private Time time;

    @BeforeEach
    void setUp() {
        time = new Time(1L, LocalTime.now());
        validRequest = new TimeRequest(time.getTime());
    }

    @Test
    @DisplayName("시간을 정상적으로 생성하는지 확인")
    void 시간을_정상적으로_생성할_수_있다() {
        // given
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
    @DisplayName("중복된 시간이 주어지면 예외를 발생시킨다")
    void 중복된_시간_예외() {
        // given
        when(timeDAO.existsTime(validRequest.time())).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> timeService.createTime(validRequest))
            .isInstanceOf(InvalidValueException.class)
            .hasMessageContaining(ErrorMessage.DUPLICATE_TIME.getMessage());
    }

    @Test
    @DisplayName("모든 시간 목록을 조회할 수 있다")
    void 시간을_모두_조회할_수_있다() {
        // given
        List<Time> mockTimes = List.of(time, new Time(2L, LocalTime.of(10, 0)));
        when(timeDAO.findTimes()).thenReturn(mockTimes);

        // when
        List<TimeResponse> responses = timeService.findTimes();

        assertAll(
            () -> assertThat(responses).hasSize(2),
            () -> {
                assertThat(responses.get(0).id()).isEqualTo(1L);
                assertThat(responses.get(0).time()).isEqualTo(time.getTime());
            },
            () -> {
                assertThat(responses.get(1).id()).isEqualTo(2L);
                assertThat(responses.get(1).time()).isEqualTo(LocalTime.of(10, 0));
            }
        );

        verify(timeDAO, times(1)).findTimes();
    }

    @Test
    @DisplayName("시간을 삭제하면 해당 시간이 삭제된다")
    void 시간_삭제_테스트() {
        // given
        Long timeId = time.getId();
        when(timeDAO.findTime(timeId)).thenReturn(time);

        // when
        timeService.deleteTime(timeId);

        // then
        verify(timeDAO, times(1)).deleteTime(timeId);
        when(timeDAO.findTime(timeId)).thenReturn(null);
        assertThat(timeService.findTimeById(timeId)).isNull();
    }
}
