package roomescape;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.DuplicateReservationException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

@SpringBootTest
class DatabaseConstraintTest {

    private static final LocalDate RESERVATION_DATE = LocalDate.of(2026, 8, 7);
    private static final LocalTime RESERVATION_TIME = LocalTime.of(10, 0);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM reservation");
        jdbcTemplate.update("DELETE FROM time");
    }

    @Test
    void 날짜_같음_시간_같음_예약_불가_테스트() {
        Time time = timeRepository.save(new Time(RESERVATION_TIME));

        reservationRepository.save(new Reservation("AAA", RESERVATION_DATE, time));

        assertThatThrownBy(() -> reservationRepository.save(new Reservation("BBB", RESERVATION_DATE, time)))
                .isInstanceOf(DuplicateReservationException.class);
    }

    @Test
    void 날짜_같음_시간_다름_예약_가능_테스트() {
        Time time = timeRepository.save(new Time(RESERVATION_TIME));
        Time anotherTime = timeRepository.save(new Time(RESERVATION_TIME.plusHours(1)));

        reservationRepository.save(new Reservation("AAA", RESERVATION_DATE, time));

        assertThatCode(() -> reservationRepository.save(new Reservation("BBB", RESERVATION_DATE, anotherTime)))
                .doesNotThrowAnyException();
    }

    @Test
    void 날짜_다름_시간_같음_예약_가능_테스트() {
        Time time = timeRepository.save(new Time(RESERVATION_TIME));

        reservationRepository.save(new Reservation("AAA", RESERVATION_DATE, time));

        assertThatCode(() -> reservationRepository.save(new Reservation("BBB", RESERVATION_DATE.plusDays(1), time)))
                .doesNotThrowAnyException();
    }
}
