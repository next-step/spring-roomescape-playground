package com.cholog.roomescape.roomescape.business.reservation;

import com.cholog.roomescape.roomescape.entity.Time;
import com.cholog.roomescape.roomescape.repository.TimeRepository;
import com.cholog.roomescape.roomescape.repository.TimeRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import com.cholog.roomescape.roomescape.entity.Reservation;
import com.cholog.roomescape.roomescape.exception.badrequest.TimeNotValidException;
import com.cholog.roomescape.roomescape.exception.conflict.ReservationConflictException;
import com.cholog.roomescape.roomescape.exception.notfound.ReservationNotFoundException;
import com.cholog.roomescape.roomescape.repository.ReservationRepository;
import com.cholog.roomescape.roomescape.repository.ReservationRepositoryImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@Import({ReservationRepositoryImpl.class, TimeRepositoryImpl.class})
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TimeRepository timeRepository;

    private LocalTime dummyTime = LocalTime.of(10, 0);
    private Time savedTime;

    @BeforeEach
    void setup() {
        savedTime = timeRepository.save(new Time(dummyTime));
    }

    @AfterEach
    void tearDown() {
        reservationRepository = null;
    }

    @Test
    @DisplayName("save()를 호출하면, ID를 갖는 객체를 반환한다.")
    void testSave() {
        // given
        Reservation reservation = new Reservation("Alice", LocalDate.now(), savedTime);

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
    @DisplayName("저장된 적 없는 시각을 참조하는 예약을 save()하면, 외래 키 제약 위반으로 예외가 발생한다.")
    void testSaveWithNotExistingTime() {
        // given
        Time notExistingTime = Time.withId(savedTime.getId() + 1L, new Time(dummyTime.plusHours(1)));
        Reservation reservation = new Reservation("Alice", LocalDate.now(), notExistingTime);

        // when & then
        assertThrows(
                TimeNotValidException.class,
                () -> reservationRepository.save(reservation)
        );
        assertThat(reservationRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("동일한 날짜, 시각의 예약을 두 번 save()하면, 유니크 제약 위반으로 예외가 발생한다.")
    void testSaveDuplicatedReservation() {
        // given
        LocalDate date = LocalDate.of(2026, 8, 31);
        reservationRepository.save(new Reservation("Alice", date, savedTime));

        Reservation duplicated = new Reservation("Alice", date, savedTime);

        // when & then
        assertThrows(
                ReservationConflictException.class,
                () -> reservationRepository.save(duplicated)
        );
        assertThat(reservationRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("날짜가 다르면 같은 시각에도 예약을 저장할 수 있다.")
    void testSaveSameTimeWithDifferentName() {
        // given
        LocalDate date = LocalDate.of(2026, 8, 31);
        reservationRepository.save(new Reservation("Alice", date, savedTime));

        // when
        Reservation saved = reservationRepository.save(new Reservation("Alice", date.plusDays(1L), savedTime));

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(reservationRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findAll()을 호출하면, 저장된 모든 레코드를 반환한다.")
    void testFindAll() {
        // given
        Reservation aliceReservation = new Reservation("Alice", LocalDate.of(2026, 8, 23), savedTime);
        Reservation bobReservation = new Reservation("Bob", LocalDate.now(), savedTime);

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
        Reservation reservation = new Reservation("Alice", LocalDate.now(), savedTime);
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
        Reservation reservation = new Reservation("Alice", LocalDate.now(), savedTime);
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
        reservationRepository.save(new Reservation("Alice", LocalDate.now(), savedTime));
        Reservation dummy = Reservation.withId(null, new Reservation("Bob", LocalDate.now(), savedTime));

        // when
        reservationRepository.delete(dummy);

        // then
        assertThat(reservationRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("delete의 인자로 저장된 적 없는 id를 가진 객체를 전달하면 조용히 실패한다.")
    void testDeleteByIllegalId() {
        // given
        reservationRepository.save(new Reservation("Alice", LocalDate.now(), savedTime));
        Reservation dummy = Reservation.withId(-1L, new Reservation("Bob", LocalDate.now(), savedTime));

        // when
        reservationRepository.delete(dummy);

        // then
        assertThat(reservationRepository.findAll().size()).isEqualTo(1);
    }
}
