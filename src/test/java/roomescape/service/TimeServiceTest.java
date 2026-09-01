package roomescape.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.domain.Time;
import roomescape.exception.NotFoundTimeException;
import roomescape.repository.TimeRepository;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TimeServiceTest {

    @Mock
    private TimeRepository timeRepository;

    @InjectMocks
    private TimeService timeService;

    @Test
    void 시간_목록을_조회한다() {
        // Given
        Time time = new Time(1L, LocalTime.of(10, 0));
        given(timeRepository.findAll()).willReturn(List.of(time));

        // When
        List<Time> actualTimes = timeService.findAll();

        // Then
        assertThat(actualTimes).hasSize(1);
        assertThat(actualTimes.get(0)).isEqualTo(time);
    }

    @Test
    void 시간을_저장한다() {
        // Given
        Time time = new Time(LocalTime.of(10, 0));
        Time savedTime = new Time(1L, LocalTime.of(10, 0));
        given(timeRepository.save(any(Time.class))).willReturn(savedTime);

        // When
        Time result = timeService.save(time);

        // Then
        assertThat(result).isEqualTo(savedTime);
    }

    @Test
    void 존재하는_id의_시간을_삭제한다() {
        // Given
        long id = 1L;
        given(timeRepository.deleteById(id)).willReturn(true);

        // When & Then
        timeService.deleteById(id);
        verify(timeRepository).deleteById(id);
    }

    @Test
    void 존재하지_않는_id의_삭제는_예외가_발생한다() {
        // Given
        long id = 999L;
        given(timeRepository.deleteById(id)).willReturn(false);

        // When & Then
        assertThatThrownBy(() -> timeService.deleteById(id))
                .isInstanceOf(NotFoundTimeException.class)
                .hasMessage("해당 id의 시간을 찾을 수 없습니다.");

        verify(timeRepository).deleteById(id);
    }
}
