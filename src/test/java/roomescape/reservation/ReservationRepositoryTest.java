package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.reservation.dto.ReservationCreateRequest;
import roomescape.reservation.dto.ReservationCreateResponse;
import roomescape.reservation.dto.ReservationSelectResponse;

@JdbcTest
@Import(ReservationRepository.class)
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void saveReservation() {
        Long timeId = saveTime("10:00");

        ReservationCreateRequest request = new ReservationCreateRequest(
                "브라운",
                Date.valueOf(LocalDate.of(2026, 8, 5)),
                timeId
        );

        ReservationCreateResponse response = reservationRepository.save(request);

        assertThat(response.id()).isNotNull();
        assertThat(response.name()).isEqualTo("브라운");
        assertThat(response.date()).isEqualTo(Date.valueOf(LocalDate.of(2026, 8, 5)));
        assertThat(response.time()).isEqualTo(timeId);
    }

    @Test
    void findAllReturnsAllReservations() {
        Long timeId1 = saveTime("10:00");
        Long timeId2 = saveTime("11:00");

        reservationRepository.save(new ReservationCreateRequest(
                "브라운",
                Date.valueOf(LocalDate.of(2026, 8, 5)),
                timeId1
        ));

        reservationRepository.save(new ReservationCreateRequest(
                "포비",
                Date.valueOf(LocalDate.of(2026, 8, 6)),
                timeId2
        ));

        Collection<ReservationSelectResponse> reservations = reservationRepository.findAll();

        assertThat(reservations).hasSize(2);

        assertThat(reservations)
                .extracting(ReservationSelectResponse::name)
                .containsExactly("브라운", "포비");

        assertThat(reservations)
                .extracting(reservation -> reservation.time().time())
                .containsExactly("10:00", "11:00");
    }

    @Test
    void deleteReservation() {
        Long timeId = saveTime("10:00");

        ReservationCreateResponse response = reservationRepository.save(new ReservationCreateRequest(
                "브라운",
                Date.valueOf(LocalDate.of(2026, 8, 5)),
                timeId
        ));

        reservationRepository.deleteById(response.id());

        assertThat(reservationRepository.findAll()).isEmpty();
    }

    @Test
    void deleteReservationWithNonExistingIdThrowsException() {
        assertThatThrownBy(() -> reservationRepository.deleteById(999L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void saveNullNameThrowsException() {
        Long timeId = saveTime("10:00");

        assertThatThrownBy(() -> reservationRepository.save(new ReservationCreateRequest(
                null,
                Date.valueOf(LocalDate.of(2026, 8, 5)),
                timeId
        ))).isInstanceOf(DataAccessException.class);
    }

    @Test
    void saveTooLongNameThrowsException() {
        Long timeId = saveTime("10:00");

        assertThatThrownBy(() -> reservationRepository.save(new ReservationCreateRequest(
                "a".repeat(256),
                Date.valueOf(LocalDate.of(2026, 8, 5)),
                timeId
        ))).isInstanceOf(DataAccessException.class);
    }

    @Test
    void saveInvalidTimeIdThrowsException() {
        assertThatThrownBy(() -> reservationRepository.save(new ReservationCreateRequest(
                "브라운",
                Date.valueOf(LocalDate.of(2026, 8, 5)),
                999L
        ))).isInstanceOf(DataAccessException.class);
    }

    private Long saveTime(String time) {
        jdbcTemplate.update("INSERT INTO time(time) VALUES (?)", time);

        return jdbcTemplate.queryForObject(
                "SELECT id FROM time WHERE time = ?",
                Long.class,
                time
        );
    }
}