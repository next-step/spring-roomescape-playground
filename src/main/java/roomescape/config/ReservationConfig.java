package roomescape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.repository.ReservationRepository;
import roomescape.service.ReservationService;
import roomescape.service.ReservationServiceImpl;

@Configuration
public class ReservationConfig {

    @Bean
    public ReservationRepository reservationRepository() {
        return new ReservationRepository();
    }

    @Bean
    public ReservationService reservationService(ReservationRepository reservationRepository) {
        return new ReservationServiceImpl(reservationRepository);
    }
}
