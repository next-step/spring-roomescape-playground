package roomescape.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

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
    private static final LocalTime NOW = LocalTime.of(10, 0);
    private static final Time TIME = new Time(1L, NOW);

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
        assertEquals(1L, first.getTime().getId());
        assertEquals(LocalTime.of(10, 0), first.getTime().getTime());
    }

    @Test
    void 예약을_저장할_수_있다() {
        jdbcTemplate.update(
                "INSERT INTO time (id, time) VALUES (?, ?)",
                1L,
                NOW
        );

        Reservation reservation = new Reservation(NAME, TODAY, TIME);
        Reservation savedReservation = reservationRepository.save(reservation);

        Long savedId = savedReservation.getId();

        Long persistedTimeId = jdbcTemplate.queryForObject(
                "SELECT time_id FROM reservation WHERE id = ?",
                Long.class,
                savedReservation.getId()
        );

        Reservation persistedReservation = jdbcTemplate.queryForObject(
                """
                SELECT id, name, date, time_id
                FROM reservation
                WHERE id = ?
                """,
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getObject("date", LocalDate.class),
                        TIME
                ),
                savedId
        );

        assertEquals(savedReservation.getId(), persistedReservation.getId());
        assertEquals(savedReservation.getName(), persistedReservation.getName());
        assertEquals(savedReservation.getDate(), persistedReservation.getDate());
        assertEquals(savedReservation.getTime().getId(), persistedTimeId);
    }

    @Test
    void 동일한_이름_날짜_시간의_예약이_존재하면_true를_반환한다() {
        jdbcTemplate.update(
                "INSERT INTO time (id, time) VALUES (?, ?)",
                TIME.getId(),
                TIME.getTime()
        );

        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                NAME,
                TODAY,
                TIME.getId()
        );

        assertTrue(reservationRepository.existsByNameAndDateAndTime(
                NAME,
                TODAY,
                TIME.getId()
        ));
    }

    @Test
    void 동일한_이름_날짜_시간의_예약이_없으면_false를_반환한다() {
        jdbcTemplate.update(
                "INSERT INTO time (id, time) VALUES (?, ?)",
                1L,
                LocalTime.of(10, 0)
        );

        jdbcTemplate.update(
                "INSERT INTO time (id, time) VALUES (?, ?)",
                2L,
                LocalTime.of(11, 0)
        );

        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                NAME,
                TODAY,
                1L
        );

        assertFalse(reservationRepository.existsByNameAndDateAndTime(
                NAME,
                TODAY,
                2L
        ));
    }

    @Test
    void 존재하는_예약_id로_삭제하면_true를_반환한다() {
        jdbcTemplate.update(
                "INSERT INTO time (id, time) VALUES (?, ?)",
                TIME.getId(),
                TIME.getTime()
        );

        Long reservationId = 100L;

        jdbcTemplate.update(
                "INSERT INTO reservation (id, name, date, time_id) VALUES (?, ?, ?, ?)",
                reservationId,
                NAME,
                TODAY,
                TIME.getId()
        );

        assertTrue(reservationRepository.deleteById(reservationId));

        assertFalse(jdbcTemplate.queryForObject(
                """
                SELECT EXISTS (
                    SELECT 1
                    FROM reservation
                    WHERE id = ?
                )
                """,
                Boolean.class,
                reservationId
        ));
    }

    @Test
    void 존재하지_않는_예약_id로_삭제하면_false를_반환한다() {
        assertFalse(reservationRepository.deleteById(NON_EXISTENT_ID));
    }

    @Test
    void 해당_시간을_사용하는_예약이_존재하면_true를_반환한다() {
        Long timeId = 1L;

        jdbcTemplate.update(
                "INSERT INTO time (id, time) VALUES (?, ?)",
                timeId,
                LocalTime.of(10, 0)
        );

        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                NAME,
                TODAY,
                timeId
        );

        assertTrue(reservationRepository.existsByTimeId(timeId));
    }

    @Test
    void 해당_시간을_사용하는_예약이_존재하지_않으면_false를_반환한다() {
        Long timeId = 1L;

        jdbcTemplate.update(
                "INSERT INTO time (id, time) VALUES (?, ?)",
                timeId,
                LocalTime.of(10, 0)
        );

        assertFalse(reservationRepository.existsByTimeId(timeId));
    }
}
