package roomescape.business;

import org.junit.jupiter.api.*;
import roomescape.entity.Reservation;
import roomescape.exception.ReservationNotFoundException;
import roomescape.repository.ReservationRepositoryV1;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReservationRepositoryV1Test {

    private ReservationRepositoryV1 reservationRepository;

    @BeforeEach
    void setUp() {
        this.reservationRepository = new ReservationRepositoryV1();
    }

    @AfterEach
    void tearDown() {
        this.reservationRepository = null;
    }

    @Test
    @DisplayName("save()를 호출하면, ID를 갖는 객체를 반환한다.")
    void testSave() {
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
    void testFindAll() {
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

    @Test
    @DisplayName("findById(Long id)를 호출하면, 해당 id의 객체를 반환한다.")
    public void testFindById() {
        // given
        Reservation reservation = new Reservation("Alice", LocalDate.now(), LocalTime.now());
        Reservation saved = reservationRepository.save(reservation);

        // when
        Reservation found = reservationRepository.findById(saved.getId())
                .orElseThrow(ReservationNotFoundException::new);

        // then
        assertThat(found).isEqualTo(saved);
    }

    @Test
    @DisplayName("저장된 적 없는 id로 findById()를 호출하면 빈 Optional을 반환한다.")
    void testFindByIdNotFound() {
        // given
        Long neverSavedId = 1L;

        // when
        Optional<Reservation> found = reservationRepository.findById(neverSavedId);

        // then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("delete를 올바르게 호출하면, 저장되어 있던 레코드를 지운다.")
    void testDelete() {
        // given
        Reservation reservation = new Reservation("Alice", LocalDate.now(), LocalTime.now());
        Reservation saved = reservationRepository.save(reservation);

        // when
        reservationRepository.delete(saved);

        // then
        assertThat(reservationRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("delete를 id가 없는 객체로 호출하면 예외가 발생한다")
    void testDeleteNotFound() {
        // given
        Reservation hasNotId = new Reservation("Alice", LocalDate.now(), LocalTime.now());

        // then
        Assertions.assertThrows(
                IllegalArgumentException.class,

                // when
                () -> reservationRepository.delete(hasNotId));
    }

    @Test
    @DisplayName("delete의 인자로 저장된 적 없는 id를 가진 객체를 전달하면 조용히 실패한다.")
    void testDeleteByIllegalId() {
        // given
        reservationRepository.save(new Reservation("Alice", LocalDate.now(), LocalTime.now()));
        Reservation dummy = Reservation.toEntityWithId(-1L, new Reservation("Bob", LocalDate.now(), LocalTime.now()));

        // when
        reservationRepository.delete(dummy);

        // then
        assertThat(reservationRepository.findAll().size()).isEqualTo(1);
    }
}
