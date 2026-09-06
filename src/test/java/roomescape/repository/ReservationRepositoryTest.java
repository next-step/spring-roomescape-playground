package roomescape.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import(ReservationRepository.class)
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String NAME = "브라운";
    private static final LocalDate TODAY = LocalDate.of(2023, 1, 2);
    private static final LocalTime NOW = LocalTime.of(10, 30);

    private static final Long NON_EXISTENT_ID = 999L;

    @Test
    @Sql(
            scripts = "/reservation-test-data.sql",
            config = @SqlConfig(encoding = "UTF-8")
    )
    void 저장된_예약_목록을_조회할_수_있다() {
        List<Reservation> reservations = reservationRepository.findAll();
        Reservation first = reservations.get(0);

        assertEquals(3, reservations.size());
        assertEquals(1L, first.getId());
        assertEquals("브라운", first.getName());
        assertEquals(LocalTime.of(10, 0), first.getTime());
    }

    @Test
    void 예약을_저장할_수_있다() {
        Reservation reservation = new Reservation(NAME, TODAY, NOW);
        Reservation savedReservation = reservationRepository.save(reservation);

        Long savedId = savedReservation.getId();

        Reservation persistedReservation = jdbcTemplate.queryForObject(
                """
                SELECT id, name, date, time
                FROM reservation
                WHERE id = ?
                """,
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getObject("date", LocalDate.class),
                        rs.getObject("time", LocalTime.class)
                ),
                savedId
        );

        assertEquals(savedReservation.getId(), persistedReservation.getId());
        assertEquals(savedReservation.getName(), persistedReservation.getName());
        assertEquals(savedReservation.getDate(), persistedReservation.getDate());
        assertEquals(savedReservation.getTime(), persistedReservation.getTime());
    }

    @Test
    void 동일한_이름_날짜_시간의_예약이_존재하면_true를_반환한다() {
        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                NAME,
                TODAY,
                NOW
        );

        assertTrue(reservationRepository.existsByNameAndDateAndTime(
                NAME,
                TODAY,
                NOW
        ));
    }

    @Test
    void 동일한_이름_날짜_시간의_예약이_없으면_false를_반환한다() {
        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                NAME,
                TODAY,
                NOW
        );

        assertFalse(reservationRepository.existsByNameAndDateAndTime(
                NAME,
                TODAY,
                NOW.plusMinutes(1)
        ));
    }

    @Test
    void 존재하는_예약_id로_삭제하면_true를_반환한다() {
        LocalTime reservationTime = NOW.plusHours(2);
        Long id = 100L;

        jdbcTemplate.update(
                "INSERT INTO reservation (id, name, date, time) VALUES (?, ?, ?, ?)",
                id,
                NAME,
                TODAY,
                reservationTime
        );

        assertTrue(reservationRepository.deleteById(id));

        assertFalse(jdbcTemplate.queryForObject(
                """
                SELECT EXISTS (
                    SELECT 1
                    FROM reservation
                    WHERE id = ?
                )
                """,
                Boolean.class,
                id
        ));
    }

    @Test
    void 존재하지_않는_예약_id로_삭제하면_false를_반환한다() {
        assertFalse(reservationRepository.deleteById(NON_EXISTENT_ID));
    }
}
