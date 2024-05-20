package roomescape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.repository.InMemoryReservationRepository;
import roomescape.repository.ReservationRepository;

@Configuration
public class ApplicationConfig {
    @Bean
    public ReservationRepository reservationRepository() {
        return new InMemoryReservationRepository();
    }
}
