package roomescape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.repository.ReservationRepository;
import roomescape.service.ReservationService;
import roomescape.service.ReservationServiceV1;

@Configuration
public class ReservationConfigV1 {

    @Bean
    public ReservationRepository reservationRepository() {
        return new ReservationRepository();
    }

    @Bean
    public ReservationService reservationService(ReservationRepository reservationRepository) {
        return new ReservationServiceV1(this.reservationRepository());
    }
}
