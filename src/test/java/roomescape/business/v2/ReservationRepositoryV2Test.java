package roomescape.business.v2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.entity.Reservation;
import roomescape.exception.ReservationNotFoundException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationRepositoryV2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
public class ReservationRepositoryV2Test {

    private ReservationRepository reservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepositoryV2(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        reservationRepository = null;
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

        Reservation aliceSavedReservation = reservationRepository.save(aliceReservation);

        // when
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        assertThat(reservations.size()).isEqualTo(1);
        assertThat(reservations.contains(aliceSavedReservation)).isTrue();
        assertThat(reservations.contains(bobReservation)).isFalse();
        assertThat(reservations.get(0).getId()).isEqualTo(aliceSavedReservation.getId());
        assertThat(reservations.get(0).getName()).isEqualTo(aliceSavedReservation.getName());
        assertThat(reservations.get(0).getDate()).isEqualTo(aliceSavedReservation.getDate());
        assertThat(reservations.get(0).getTime()).isEqualTo(aliceSavedReservation.getTime());
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
    @DisplayName("delete를 id가 없는 객체로 호출하면 조용히 실패한다.")
    void testDeleteNotFound() {
        // given
        reservationRepository.save(new Reservation("Alice", LocalDate.now(), LocalTime.now()));
        Reservation dummy = Reservation.withId(null, new Reservation("Bob", LocalDate.now(), LocalTime.now()));

        // when
        reservationRepository.delete(dummy);

        // then
        assertThat(reservationRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("delete의 인자로 저장된 적 없는 id를 가진 객체를 전달하면 조용히 실패한다.")
    void testDeleteByIllegalId() {
        // given
        reservationRepository.save(new Reservation("Alice", LocalDate.now(), LocalTime.now()));
        Reservation dummy = Reservation.withId(-1L, new Reservation("Bob", LocalDate.now(), LocalTime.now()));

        // when
        reservationRepository.delete(dummy);

        // then
        assertThat(reservationRepository.findAll().size()).isEqualTo(1);
    }
}
