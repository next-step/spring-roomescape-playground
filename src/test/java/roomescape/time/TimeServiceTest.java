package roomescape.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.exception.NotFoundException;
import roomescape.reservation.ReservationRepository;
import roomescape.time.dto.TimeCreateRequest;
import roomescape.time.dto.TimeResponse;

@JdbcTest
@Import({TimeRepository.class, ReservationRepository.class, TimeService.class})
class TimeServiceTest {

    @Autowired
    private TimeService timeService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void createTime() {
        TimeResponse response = timeService.create(new TimeCreateRequest("10:00"));

        assertThat(response.id()).isNotNull();
        assertThat(response.time()).isEqualTo("10:00");
    }

    @Test
    void findAllTimes() {
        timeService.create(new TimeCreateRequest("10:00"));
        timeService.create(new TimeCreateRequest("11:00"));

        Collection<TimeResponse> times = timeService.findAll();

        assertThat(times)
                .extracting(TimeResponse::time)
                .containsExactly("10:00", "11:00");
    }

    @Test
    void createDuplicateTimeThrowsIllegalArgumentException() {
        timeService.create(new TimeCreateRequest("10:00"));

        assertThatThrownBy(() -> timeService.create(new TimeCreateRequest("10:00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 시간입니다.");
    }

    @Test
    void deleteTime() {
        TimeResponse response = timeService.create(new TimeCreateRequest("10:00"));

        timeService.deleteById(response.id());

        assertThat(timeService.findAll()).isEmpty();
    }

    @Test
    void deleteTimeWithReservationThrowsIllegalArgumentException() {
        TimeResponse response = timeService.create(new TimeCreateRequest("10:00"));
        jdbcTemplate.update(
                "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)",
                "브라운",
                "2026-08-05",
                response.id()
        );

        assertThatThrownBy(() -> timeService.deleteById(response.id()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약이 존재하는 시간은 삭제할 수 없습니다.");

        assertThat(timeService.findAll()).hasSize(1);
    }

    @Test
    void deleteNonExistingTimeThrowsNotFoundException() {
        assertThatThrownBy(() -> timeService.deleteById(999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 id입니다.");
    }
}
