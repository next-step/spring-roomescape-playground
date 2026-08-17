package roomescape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationRepositoryV1;
import roomescape.service.ReservationService;
import roomescape.service.ReservationServiceV1;

@Configuration
@Profile("v1")
public class ReservationConfigV1 {

    @Bean
    public ReservationRepository reservationRepository() {
        return new ReservationRepositoryV1();
    }

    @Bean
    public ReservationService reservationService(ReservationRepository reservationRepository) {
        return new ReservationServiceV1(reservationRepository);
    }
}
