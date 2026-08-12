package roomescape;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryReservationRepositoryTest {
    private InMemoryReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new InMemoryReservationRepository();
    }

    @Test
    void 예약을_저장하면_id가_부여되고_목록에_추가된다() {
        // given
        Reservation reservation = new Reservation(null, "브라운", LocalDate.now(), LocalTime.of(10, 0));

        // when
        Reservation savedReservation = reservationRepository.save(reservation);

        // then
        assertThat(savedReservation.getId()).isNotNull();
        assertThat(savedReservation.getName()).isEqualTo("브라운");
        assertThat(reservationRepository.findAll()).hasSize(4);
    }

    @Test
    void 같은_날짜와_시간의_예약이_존재하면_true를_반환한다() {
        // given
        Reservation reservation = new Reservation(null, "브라운", LocalDate.now(), LocalTime.of(10, 0));

        // when
        reservationRepository.save(reservation);

        // then
        assertThat(reservationRepository.existsByDateAndTime(reservation.getDate(), reservation.getTime()))
                .isTrue();
    }

    @Test
    void 같은_날짜와_시간의_예약이_존재하지_않으면_false를_반환한다() {
        assertThat(reservationRepository.existsByDateAndTime(LocalDate.now(), LocalTime.of(10, 0)))
                .isFalse();
    }

    @Test
    void 예약을_삭제하면_목록에서_제거된다() {
        // given
        Reservation reservation = reservationRepository.findById(1L).get();

        // when
        reservationRepository.delete(reservation);

        // then
        assertThat(reservationRepository.findById(1L)).isEmpty();
    }

    @Test
    void id에_해당하는_예약이_존재하면_예약을_반환한다() {
        // given
        Long id = 1L;

        // when
        Optional<Reservation> reservation = reservationRepository.findById(id);

        // then
        assertThat(reservation).isPresent();
        assertThat(reservation.get().getId()).isEqualTo(id);
    }

    @Test
    void id에_해당하는_예약이_존재하지_않으면_빈_Optional을_반환한다() {
        // given
        Long nonExistingId = 999L;

        // when
        Optional<Reservation> reservation = reservationRepository.findById(nonExistingId);

        // then
        assertThat(reservation).isEmpty();
    }
}
