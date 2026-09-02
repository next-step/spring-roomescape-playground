package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ReservationRepositoryTest {
    private static final LocalDate DATE = LocalDate.of(2027, 8, 14);
    private static final LocalTime TIME = LocalTime.of(10, 0);

    private ReservationRepository reservationRepository;
    private TimeRepository timeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationRepository = new JdbcReservationRepository(jdbcTemplate);
        timeRepository = new JdbcTimeRepository(jdbcTemplate);
    }

    @Test
    void 예약을_저장하면_id가_부여되고_목록에_추가된다() {
        // given
        Time time = saveTime();
        Reservation reservation = new Reservation(null, "브라운", DATE, time);
        int size = reservationRepository.findAll().size();

        // when
        Reservation savedReservation = reservationRepository.save(reservation);

        // then
        assertThat(savedReservation.getId()).isNotNull();
        assertThat(savedReservation.getName()).isEqualTo("브라운");
        assertThat(reservationRepository.findAll()).hasSize(size + 1);
    }

    @Test
    void 같은_날짜와_시간의_예약이_존재하면_true를_반환한다() {
        // given
        Time time = saveTime();
        Reservation reservation = new Reservation(null, "브라운", DATE, time);

        // when
        reservationRepository.save(reservation);

        // then
        assertThat(reservationRepository.existsByDateAndTimeId(reservation.getDate(), reservation.getTime().getId()))
                .isTrue();
    }

    @Test
    void 같은_날짜와_시간의_예약이_존재하지_않으면_false를_반환한다() {
        Time time = saveTime();

        assertThat(reservationRepository.existsByDateAndTimeId(DATE, time.getId()))
                .isFalse();
    }

    @Test
    void 예약을_삭제하면_목록에서_제거된다() {
        // given
        Time time = saveTime();
        Reservation reservation = new Reservation(null, "브라운", DATE, time);
        Reservation savedReservation = reservationRepository.save(reservation);

        // when
        boolean deleted = reservationRepository.deleteById(savedReservation.getId());

        // then
        assertThat(deleted).isTrue();
        assertThat(reservationRepository.findById(savedReservation.getId())).isEmpty();
    }

    @Test
    void id에_해당하는_예약이_없으면_삭제_시_false를_반환한다() {
        // given
        Long id = 999L;

        // when
        boolean deleted = reservationRepository.deleteById(id);

        // then
        assertThat(deleted).isFalse();
    }

    @Test
    void id에_해당하는_예약이_존재하면_예약을_반환한다() {
        // given
        Time time = saveTime();
        Reservation reservation = new Reservation(null, "브라운", DATE, time);
        Reservation savedReservation = reservationRepository.save(reservation);

        // when
        Optional<Reservation> foundReservation = reservationRepository.findById(savedReservation.getId());

        // then
        assertThat(foundReservation).isPresent();
        assertThat(foundReservation.get().getId()).isEqualTo(savedReservation.getId());
    }

    @Test
    void id에_해당하는_예약이_존재하지_않으면_빈_Optional을_반환한다() {
        // given
        Long nonExistingId = -1L;

        // when
        Optional<Reservation> reservation = reservationRepository.findById(nonExistingId);

        // then
        assertThat(reservation).isEmpty();
    }

    private Time saveTime() {
        return timeRepository.save(new Time(TIME));
    }
}
