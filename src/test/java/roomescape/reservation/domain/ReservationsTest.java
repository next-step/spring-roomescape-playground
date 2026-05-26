package roomescape.reservation.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import roomescape.reservation.repository.ReservationsRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Sql("/schema.sql")
public class ReservationsTest {
    private final ReservationsRepository reservationsRepository;
    private final Reservations reservations;

    @Autowired
    public ReservationsTest(JdbcTemplate jdbcTemplate) {
        reservationsRepository = new ReservationsRepository(jdbcTemplate);
        reservations = new Reservations(reservationsRepository);
    }

    @Test
    void 예약을_추가한_후_조회할_수_있다() {
        CreateReservationInfo hola = new CreateReservationInfo(
                "hola",
                LocalDateTime.of(1026, 5, 7, 11, 13)
        );

        CreateReservationInfo gracia = new CreateReservationInfo(
                "gracia",
                LocalDateTime.of(1026, 8, 2, 22, 13)
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
    void 예약을_삭제할_수_있다() throws ReservationException {
        CreateReservationInfo info = new CreateReservationInfo(
                "aaa",
                LocalDateTime.of(2026, 5, 7, 11, 13)
        );
        Reservation reservation = createFresh(info);

        assertThatCode(() -> reservations.delete(reservation.getId()))
                .doesNotThrowAnyException();

        assertThat(reservations.getAll())
                .noneMatch(existing -> existing.getId().equals(reservation.getId()));
    }

    @Test
    void 같은_시간에_예약을_중복으로_추가할_수_없다() throws ReservationException {
        CreateReservationInfo info = new CreateReservationInfo(
                "bbb",
                LocalDateTime.of(2026, 5, 7, 11, 13)
        );
        createFresh(info);

        assertThatThrownBy(() -> reservations.create(info))
                .isInstanceOf(ReservationException.DuplicateTime.class);
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
        Reservation previous = reservationsRepository.getByTime(reservation.time());
        if (previous != null) {
            reservationsRepository.delete(previous.getId());
        }
        return reservations.create(reservation);
    }
}
