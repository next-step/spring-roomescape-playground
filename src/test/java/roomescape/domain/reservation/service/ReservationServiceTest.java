package roomescape.domain.reservation.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.reservation.dto.ReservationRequest;
import roomescape.domain.reservation.dto.ReservationResponse;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.reservationTime.repository.ReservationTimeRepository;
import roomescape.global.exception.RoomescapeBadRequestException;
import roomescape.global.exception.RoomescapeServerException;

@JdbcTest
class ReservationServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private ReservationRepository reservationRepository;
    private ReservationTimeRepository reservationTimeRepository;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository(jdbcTemplate, dataSource);
        reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate, dataSource);
        reservationService = new ReservationService(reservationRepository, reservationTimeRepository);
    }

    @Test
    void getReservationResponses_테스트() {
        jdbcTemplate.update(
                "INSERT INTO reservationTime (id, time) VALUES (?, ?), (?, ?), (?, ?)",
                0L, LocalTime.of(11, 0),
                1L, LocalTime.of(12, 0),
                2L, LocalTime.of(13, 0)
        );
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05", 0L);
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "커찬", "2024-08-05", 1L);
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "망고", "2025-08-05", 2L);

        List<ReservationResponse> result = reservationService.getReservationResponses();

        assertThat(result.size()).isEqualTo(3);
        assertThat(result).extracting(ReservationResponse::name)
                .contains("브라운", "커찬", "망고");
    }

    @Test
    void createReservation_테스트() {
        jdbcTemplate.update("INSERT INTO reservationTime (id, time) VALUES (?, ?)", 0L, LocalTime.of(11, 0));
        ReservationRequest request = new ReservationRequest("망고", LocalDate.of(2025, 10, 11), 0L);

        ReservationResponse result = reservationService.createReservation(request);
        Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap("SELECT * FROM reservation WHERE id = ?",
                result.id());
        String name = (String) stringObjectMap.get("name");
        LocalDate date = ((java.sql.Date) stringObjectMap.get("date")).toLocalDate();
        Long timeId = (Long) stringObjectMap.get("time_id");

        assertThat(name).isEqualTo(request.name());
        assertThat(date).isEqualTo(request.date());
        assertThat(timeId).isEqualTo(request.time());
    }

    @DisplayName("과거 시간에 예약하면 오류가 발생")
    @Test
    void beforeDateError_테스트() {
        jdbcTemplate.update("INSERT INTO reservationTime (id, time) VALUES (?, ?)", 0L, LocalTime.of(11, 0));
        ReservationRequest request = new ReservationRequest("망고", LocalDate.of(2024, 10, 11), 0L);

        assertThatThrownBy(() -> reservationService.createReservation(request))
                .isInstanceOf(RoomescapeBadRequestException.class);
    }

    @DisplayName("당일 예약인 경우 현재 시각보다 빠른 시간에 예약하면 오류가 발생")
    @Test
    void beforeTimeError_테스트() {
        jdbcTemplate.update("INSERT INTO reservationTime (id, time) VALUES (?, ?)", 0L,
                LocalTime.now().minusMinutes(10));
        ReservationRequest request = new ReservationRequest("망고", LocalDate.now(), 0L);

        assertThatThrownBy(() -> reservationService.createReservation(request))
                .isInstanceOf(RoomescapeBadRequestException.class);
    }

    @Test
    void deleteReservation_테스트() {
        Long id = deleteTestSetup();

        reservationService.deleteReservation(id);
        Integer afterSize = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(afterSize).isEqualTo(0);
    }

    private Long deleteTestSetup() {
        jdbcTemplate.update("INSERT INTO reservationTime (id, time) VALUES (?, ?)", 0L, LocalTime.of(11, 0));
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05", 0L);

        return jdbcTemplate.queryForObject("SELECT id FROM reservation WHERE name = ?", Long.class, "브라운");
    }
}
