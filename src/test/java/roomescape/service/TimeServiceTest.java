package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import roomescape.domain.ReservationTime;
import roomescape.exception.DuplicateReservationTimeException;
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
    @DisplayName("입력받은 시간으로 예약 시간을 생성한다")
    void createsReservationTime() {
        LocalTime time = LocalTime.of(15, 40);
        when(timeRepository.save(any(ReservationTime.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ReservationTime reservationTime = timeService.create(time);

        assertThat(reservationTime.getTime()).isEqualTo(time);
        verify(timeRepository).save(reservationTime);
    }

    @Test
    @DisplayName("같은 예약 시간은 중복으로 생성할 수 없다")
    void rejectsDuplicateReservationTime() {
        LocalTime time = LocalTime.of(15, 40);
        when(timeRepository.existsByTime(time)).thenReturn(true);

        assertThatThrownBy(() -> timeService.create(time))
                .isInstanceOf(DuplicateReservationTimeException.class)
                .hasMessage("이미 등록된 시간입니다.");
        verify(timeRepository, never()).save(any(ReservationTime.class));
    }

    @Test
    @DisplayName("동시에 같은 예약 시간이 저장되어도 중복 시간 예외로 변환한다")
    void translatesUniqueConstraintViolation() {
        LocalTime time = LocalTime.of(15, 40);
        when(timeRepository.save(any(ReservationTime.class)))
                .thenThrow(new DataIntegrityViolationException("unique constraint violation"));

        assertThatThrownBy(() -> timeService.create(time))
                .isInstanceOf(DuplicateReservationTimeException.class)
                .hasMessage("이미 등록된 시간입니다.");
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
