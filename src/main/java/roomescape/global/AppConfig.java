package roomescape.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.domain.reservation.dao.JdbcReservationRepository;
import roomescape.domain.reservation.dao.ReservationRepository;
import roomescape.domain.time.dao.JdbcTimeRepository;
import roomescape.domain.time.dao.TimeRepository;

@Configuration
public class AppConfig {

    @Bean
    public ReservationRepository reservationRepository() {
        return new JdbcReservationRepository();
    }

    @Bean
    public TimeRepository timeRepository() {
        return new JdbcTimeRepository();
    }
}
