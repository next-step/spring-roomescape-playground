package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.reservation.controller.ReservationController;

public class LayerTest extends RoomescapeApplicationTest {

    @Autowired
    private ReservationController reservationController;

    @Test
    @DisplayName("Controller는 JdbcTemplate에 의존하지 않는다.")
    public void Controller는_JdbcTemplate에_의존하지_않는다() {
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
