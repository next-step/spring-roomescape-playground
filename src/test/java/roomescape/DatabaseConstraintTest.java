package roomescape;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.repository.JdbcReservationRepository;

@SpringBootTest
class DatabaseConstraintTest {

    private static final LocalDate RESERVATION_DATE = LocalDate.of(2026, 8, 7);
    private static final LocalTime RESERVATION_TIME = LocalTime.of(10, 0);

    @Autowired
    private JdbcReservationRepository jdbcReservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 날짜_같음_시간_같음_예약_불가_테스트() {
        jdbcReservationRepository.save(new Reservation("AAA", RESERVATION_DATE, RESERVATION_TIME));

        assertThatThrownBy(() -> jdbcReservationRepository.save(new Reservation("BBB", RESERVATION_DATE, RESERVATION_TIME)))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void 날짜_같음_시간_다름_예약_가능_테스트() {
        jdbcReservationRepository.save(new Reservation("AAA", RESERVATION_DATE, RESERVATION_TIME));

        assertThatCode(() -> jdbcReservationRepository.save(new Reservation("BBB", RESERVATION_DATE, RESERVATION_TIME.plusHours(1))))
                .doesNotThrowAnyException();
    }

    @Test
    void 날짜_다름_시간_같음_예약_가능_테스트() {
        jdbcReservationRepository.save(new Reservation("AAA", RESERVATION_DATE, RESERVATION_TIME));

        assertThatCode(() -> jdbcReservationRepository.save(new Reservation("BBB", RESERVATION_DATE.plusDays(1), RESERVATION_TIME)))
                .doesNotThrowAnyException();
    }
}
