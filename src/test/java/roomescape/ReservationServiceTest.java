package roomescape;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.service.ReservationService;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Test
    void id가_null이면_예외_테스트() {
        assertThatThrownBy(() -> reservationService.deleteById(null))
                .isInstanceOf(NullPointerException.class);
    }
}
