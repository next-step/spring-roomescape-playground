package roomescape.business;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReservationRepositoryTest {

    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp() {
        this.reservationRepository = new ReservationRepository();
    }

    @AfterEach
    public void tearDown() {
        this.reservationRepository = null;
    }

    @Test
    @DisplayName("save()를 호출하면, ID를 갖는 객체를 반환한다.")
    public void testSave() {
        // given
        Reservation reservation = new Reservation("Alice", LocalDate.now(), LocalTime.now());

        // when
        Reservation saved = reservationRepository.save(reservation);

        // then
        assertThat(reservation.getId()).isNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo(reservation.getName());
        assertThat(saved.getDate()).isEqualTo(reservation.getDate());
        assertThat(saved.getTime()).isEqualTo(reservation.getTime());
    }

    @Test
    @DisplayName("findAll()을 호출하면, 저장된 모든 레코드를 반환한다.")
    public void testFindAll() {
        // given
        Reservation aliceReservation = new Reservation("Alice", LocalDate.now(), LocalTime.now());
        Reservation bobReservation = new Reservation("Bob", LocalDate.now(), LocalTime.now());

        Reservation aliceSavedResevation = reservationRepository.save(aliceReservation);

        // when
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        assertThat(reservations.size()).isEqualTo(1);
        assertThat(reservations.contains(aliceSavedResevation)).isTrue();
        assertThat(reservations.contains(bobReservation)).isFalse();
        assertThat(reservations.get(0).getId()).isEqualTo(aliceSavedResevation.getId());
        assertThat(reservations.get(0).getName()).isEqualTo(aliceSavedResevation.getName());
        assertThat(reservations.get(0).getDate()).isEqualTo(aliceSavedResevation.getDate());
        assertThat(reservations.get(0).getTime()).isEqualTo(aliceSavedResevation.getTime());

    }
}
