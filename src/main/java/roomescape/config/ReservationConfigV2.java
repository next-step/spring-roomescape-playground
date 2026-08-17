package roomescape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationRepositoryV2;
import roomescape.service.ReservationService;
import roomescape.service.ReservationServiceV2;

@Configuration
@Profile("v2")
public class ReservationConfigV2 {

    @Bean
    public ReservationRepository reservationRepository(JdbcTemplate jdbcTemplate) {
        return new ReservationRepositoryV2(jdbcTemplate);
    }

    @Bean
    public ReservationService reservationService(ReservationRepository reservationRepository) {
        return new ReservationServiceV2(reservationRepository);
    }
}
