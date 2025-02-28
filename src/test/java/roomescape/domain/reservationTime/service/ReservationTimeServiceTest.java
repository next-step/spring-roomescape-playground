package roomescape.domain.reservationTime.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.reservationTime.dto.ReservationTimeRequest;
import roomescape.domain.reservationTime.dto.ReservationTimeResponse;
import roomescape.domain.reservationTime.repository.ReservationTimeRepository;
import roomescape.global.exception.RoomescapeBadRequestException;
import roomescape.global.exception.RoomescapeServerException;

@JdbcTest
class ReservationTimeServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private ReservationTimeRepository reservationTimeRepository;
    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate, dataSource);
        reservationTimeService = new ReservationTimeService(reservationTimeRepository);
    }

    @Test
    void createReservationTime_테스트() {
        ReservationTimeRequest request = new ReservationTimeRequest(LocalTime.of(11, 00));

        ReservationTimeResponse reservationTime = reservationTimeService.createReservationTime(request);

        assertThat(reservationTime.time()).isEqualTo(LocalTime.of(11, 00));
        assertThat(reservationTime.id()).isNotNull();
    }

    @DisplayName("조회 결과를 오름차순으로 보여준다.")
    @Test
    void getReservationTimes_테스트() {
        LocalTime time = LocalTime.of(11, 00);
        LocalTime time2 = LocalTime.of(12, 00);
        LocalTime time3 = LocalTime.of(13, 00);
        jdbcTemplate.update(
                "INSERT INTO reservationTime (id, time) VALUES (?, ?), (?, ?), (?, ?)",
                0L, time,
                1L, time2,
                2L, time3
        );

        List<ReservationTimeResponse> reservationTimes = reservationTimeService.getReservationTimes();

        assertThat(reservationTimes.size()).isEqualTo(3);
        assertThat(reservationTimes)
                .extracting(ReservationTimeResponse::time)
                .containsExactly(time, time2, time3);
    }

    @Test
    void deleteReservationTime_테스트() {
        Long id = deleteTestSetup();

        reservationTimeService.deleteReservationTime(id);
        Integer afterSize = jdbcTemplate.queryForObject("SELECT count(1) from reservationTime", Integer.class);

        assertThat(afterSize).isEqualTo(0);
    }

    private Long deleteTestSetup() {
        LocalTime time = LocalTime.of(11, 00);
        jdbcTemplate.update("INSERT INTO reservationTime (id, time) VALUES (?, ?)", 0L, time);

        return jdbcTemplate.queryForObject("SELECT id FROM reservationTime WHERE time = ?", Long.class, time);
    }
}
