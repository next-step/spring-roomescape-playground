package roomescape.repository.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservedDateTime;

@JdbcTest
@Import(ReservationDatabaseRepository.class)
@ActiveProfiles(value = "test")
class ReservationDatabaseRepositoryTest {

    @Autowired
    private ReservationDatabaseRepository reservationDatabaseRepository;

    private static final LocalDate reservedDate = LocalDate.now().plusDays(10);

    private static final LocalTime reservedTime = LocalTime.now();

    @Test
    @DisplayName("RESERVATION 객체를 데이터베이스에 저장 시 같은 객체이고 id가 not null")
    void givenReservationWhenSaveThenEqualsAndIdNotNull() {
        //given
        Reservation reservation = createReservation();
        // when
        Reservation savedReservation = reservationDatabaseRepository.save(reservation);
        // then
        Assertions.assertAll(
                () -> assertThat(savedReservation.getName()).isEqualTo(reservation.getName()),
                () -> assertThat(savedReservation.getId()).isNotNull()
        );
    }

    @Test
    @DisplayName("RESERVATION 객체를 데이터베이스에 저장 후 id로 찾을 때 동일해야한다.")
    void givenReservationSaveWhenFindByIdThenEquals() {
        //given
        Reservation savedReservation = reservationDatabaseRepository.save(createReservation());
        // when
        Reservation foundReservation =
                reservationDatabaseRepository.findById(savedReservation.getId()).orElseThrow();
        // then
        assertThat(foundReservation).isEqualTo(savedReservation);
        assertThat(foundReservation.getId()).isEqualTo(savedReservation.getId());
    }

    @Test
    @DisplayName("RESERVATION 객체를 데이터베이스에 1개 저장 후 모두 찾을 때 개수가 1개여야한다.")
    void givenReservationsSaveWhenFindAllThenEqualsCount() {
        //given
        Reservation savedReservation = reservationDatabaseRepository.save(createReservation());
        // when
        List<Reservation> reservations =
                reservationDatabaseRepository.findAll();
        // then
        assertThat(reservations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("RESERVATION 저장 후 삭제 시 해당 데이터는 사라져야한다.")
    void givenReservationOneWhenDeleteThenEqualsCount() {
        //given
        Reservation savedReservation = reservationDatabaseRepository.save(createReservation());
        // when
        reservationDatabaseRepository.delete(savedReservation);
        List<Reservation> reservations = reservationDatabaseRepository.findAll();
        // then
        assertThat(reservations.size()).isEqualTo(0);
    }

    private Reservation createReservation() {
        return new Reservation(null, "곰곰", new ReservedDateTime(reservedDate, reservedTime));
    }
}
