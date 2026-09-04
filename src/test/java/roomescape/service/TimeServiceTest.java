package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.exception.TimeInUseException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

class TimeServiceTest {

    private TimeRepository timeRepository;
    private ReservationRepository reservationRepository;
    private TimeService timeService;

    @BeforeEach
    void setUp() {
        timeRepository = mock(TimeRepository.class);
        reservationRepository = mock(ReservationRepository.class);
        timeService = new TimeService(timeRepository, reservationRepository);
    }

    @Test
    @DisplayName("예약에서 사용 중인 시간은 삭제할 수 없다")
    void rejectsDeletingTimeInUse() {
        when(reservationRepository.existsByTimeId(1L)).thenReturn(true);

        assertThatThrownBy(() -> timeService.deleteById(1L))
                .isInstanceOf(TimeInUseException.class);
        verify(timeRepository, never()).deleteById(1L);
    }
}
