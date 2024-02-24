package roomescape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.repository.ReservationRepository;
import roomescape.service.DefaultReservationService;
import roomescape.service.JdbcReservationService;
import roomescape.service.ReservationService;

@Configuration
public class ReservationServiceConfig {
	@Bean
	@Profile("jdbc")
	public ReservationService JdbcReservationService(JdbcTemplate jdbcTemplate) {
		return new JdbcReservationService(jdbcTemplate);
	}

	@Bean
	@Profile("default")
	public ReservationService DefaultRefaultReservationService(ReservationRepository reservationRepository) {
		return new DefaultReservationService(reservationRepository);
	}
}
