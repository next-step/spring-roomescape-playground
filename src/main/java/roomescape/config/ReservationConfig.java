package roomescape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationRepositoryImpl;
import roomescape.service.ReservationService;
import roomescape.service.ReservationServiceImpl;

@Configuration
@Profile("v2")
public class ReservationConfig {

    @Bean
    public ReservationRepository reservationRepository(JdbcTemplate jdbcTemplate) {
        return new ReservationRepositoryImpl(jdbcTemplate);
    }

    @Bean
    public ReservationService reservationService(ReservationRepository reservationRepository) {
        return new ReservationServiceImpl(reservationRepository);
    }
}
