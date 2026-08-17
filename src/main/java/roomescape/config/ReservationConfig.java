package roomescape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.repository.ReservationRepositoryV1;
import roomescape.service.ReservationService;
import roomescape.service.ReservationServiceImpl;

@Configuration
public class ReservationConfig {

    @Bean
    public ReservationRepositoryV1 reservationRepository() {
        return new ReservationRepositoryV1();
    }

    @Bean
    public ReservationService reservationService(ReservationRepositoryV1 reservationRepository) {
        return new ReservationServiceImpl(reservationRepository);
    }
}
