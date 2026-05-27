package roomescape.reservation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReservationArchitectureTest {
    @Autowired
    private ReservationController reservationController;

    /**
     * 스터디 10단계 요구사항
     */
    @Test
    void 컨트롤러가_DB에_바로_접근하지_않는다() {
        boolean isJdbcTemplateInjected = false;

        for (Field field : reservationController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }
}
