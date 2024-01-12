package roomescape.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationsTest {

    private final Reservations reservations = new Reservations();

    @Test
    void 예약을_추가한다() {
        Reservation saved = reservations.add(new Reservation("성철", LocalDate.now(), "11:11"));

        assertThat(reservations.getAll()).contains(saved);
    }

    @Test
    void 예약을_추가하면_id가_부여된다() {
        Reservation saved = reservations.add(new Reservation("성철", LocalDate.now(), "11:11"));

        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void 예약_추가시_id는_1부터_순차_부여된다() {
        Reservation saved = reservations.add(new Reservation("성철", LocalDate.now(), "11:11"));
        Reservation other = reservations.add(new Reservation("성철", LocalDate.now(), "12:12"));

        assertThat(saved.getId()).isEqualTo(1);
        assertThat(other.getId()).isEqualTo(2);
    }

    @Test
    void 예약을_취소한다() {
        Reservation saved = reservations.add(new Reservation("성철", LocalDate.now(), "11:11"));

        reservations.cancel(saved.getId());

        assertThat(reservations.getAll()).doesNotContain(saved);
    }
}
