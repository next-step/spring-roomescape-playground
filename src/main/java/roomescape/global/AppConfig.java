package roomescape.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.domain.reservation.dao.JdbcReservationRepository;
import roomescape.domain.reservation.dao.ReservationRepository;

@Configuration
public class AppConfig {

    @Bean
    public ReservationRepository reservationRepository() {
        return new JdbcReservationRepository();
    }
}
