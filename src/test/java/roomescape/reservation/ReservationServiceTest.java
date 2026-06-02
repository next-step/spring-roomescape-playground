package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.reservation.dto.ReservationCreateRequest;
import roomescape.time.TimeRepository;
import roomescape.time.domain.Time;

@JdbcTest
@Import({ReservationService.class, ReservationRepository.class, TimeRepository.class})
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TimeRepository timeRepository;

    @Test
    void createDuplicateDateAndTimeThrowsIllegalArgumentException() {
        Time time = timeRepository.save(new Time(null, "10:00"));
        ReservationCreateRequest request = new ReservationCreateRequest(
                "브라운",
                "2026-08-05",
                time.getId()
        );
        reservationService.create(request);

        assertThatThrownBy(() -> reservationService.create(new ReservationCreateRequest(
                "포비",
                "2026-08-05",
                time.getId()
        )))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 예약된 날짜와 시간입니다.");
    }
}
