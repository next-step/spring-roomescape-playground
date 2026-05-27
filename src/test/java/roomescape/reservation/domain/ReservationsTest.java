package roomescape.reservation.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import roomescape.reservation.repository.ReservationsRepository;
import roomescape.time.domain.CreateTimeInfo;
import roomescape.time.domain.TimeId;
import roomescape.time.domain.Times;
import roomescape.time.repository.TimesRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Sql({"/initialize-test.sql", "/schema.sql"})
public class ReservationsTest {
    private final ReservationsRepository reservationsRepository;
    private final Reservations reservations;
    private final Times times;

    @Autowired
    public ReservationsTest(JdbcTemplate jdbcTemplate) {
        reservationsRepository = new ReservationsRepository(jdbcTemplate);
        times = new Times(new TimesRepository(jdbcTemplate));
        reservations = new Reservations(reservationsRepository, times);
    }

    TimeId time1;
    TimeId time2;

    @BeforeEach
    void setup() {
        time1 = times.create(new CreateTimeInfo(LocalTime.of(10, 30))).getId();
        time2 = times.create(new CreateTimeInfo(LocalTime.of(15, 29))).getId();
    }

    @Test
    void 예약을_추가한_후_조회할_수_있다() {
        CreateReservationInfo hola = new CreateReservationInfo(
                "hola",
                LocalDate.of(1026, 5, 7),
                time1
        );

        CreateReservationInfo gracia = new CreateReservationInfo(
                "gracia",
                LocalDate.of(1026, 8, 2),
                time2
        );

        assertThatCode(() -> {
            reservations.create(hola);
            reservations.create(gracia);
        }).doesNotThrowAnyException();

        assertThat(reservations.getAll())
                .anyMatch(reservation -> reservation.getName().equals(hola.name()))
                .anyMatch(reservation -> reservation.getName().equals(gracia.name()));
    }

    @Test
    void 예약을_삭제할_수_있다() {
        CreateReservationInfo info = new CreateReservationInfo(
                "aaa",
                LocalDate.of(2026, 5, 7),
                time1
        );
        Reservation reservation = createFresh(info);

        assertThatCode(() -> reservations.delete(reservation.getId()))
                .doesNotThrowAnyException();

        assertThat(reservations.getAll())
                .noneMatch(existing -> existing.getId().equals(reservation.getId()));
    }

    @Test
    void 존재하지_않는_시간에_예약을_추가할_수_없다() {
        CreateReservationInfo info = new CreateReservationInfo(
                "helloWorld",
                LocalDate.of(2026, 11, 13),
                new TimeId(12345)
        );

        assertThatThrownBy(() -> reservations.create(info))
                .isInstanceOf(ReservationException.TimeNotFound.class);
    }

    @Test
    void 같은_시간에_예약을_중복으로_추가할_수_없다() {
        CreateReservationInfo info = new CreateReservationInfo(
                "bbb",
                LocalDate.of(2026, 5, 7),
                time2
        );
        createFresh(info);

        assertThatThrownBy(() -> reservations.create(info))
                .isInstanceOf(ReservationException.DuplicateDateTime.class);
    }

    @Test
    void 존재하지_않는_예약을_삭제할_수_없다() {
        long nonExistingId = reservationsRepository.getAll().stream()
                .mapToLong(reservation -> reservation.getId().id())
                .max().orElse(0) + 1;


        assertThatThrownBy(() -> reservations.delete(new ReservationId(nonExistingId)))
                .isInstanceOf(ReservationException.DoesNotExist.class);
    }


    private Reservation createFresh(CreateReservationInfo reservation) {
        Reservation previous = reservationsRepository.getByDateTime(reservation.date(), reservation.timeId());
        if (previous != null) {
            reservationsRepository.delete(previous.getId());
        }
        return reservations.create(reservation);
    }
}
