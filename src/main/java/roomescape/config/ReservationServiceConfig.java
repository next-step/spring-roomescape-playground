package roomescape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;
import roomescape.service.DefaultReservationService;
import roomescape.service.DefaultTimeService;
import roomescape.service.JdbcReservationService;
import roomescape.service.JdbcTimeService;
import roomescape.service.ReservationService;
import roomescape.service.TimeService;

@Configuration
public class ReservationServiceConfig {
	@Bean
	@Profile("jdbc")
	public ReservationService JdbcReservationService(JdbcTemplate jdbcTemplate) {
		return new JdbcReservationService(jdbcTemplate);
	}

	@Bean
	@Profile("jdbc")
	public TimeService JdbcTimeService(JdbcTemplate jdbcTemplate) {
		return new JdbcTimeService(jdbcTemplate);
	}

	@Bean
	@Profile("default")
	public ReservationService DefaultRefaultReservationService(ReservationRepository reservationRepository,
															   TimeRepository timeRepository) {
		return new DefaultReservationService(reservationRepository, timeRepository);
	}

	@Bean
	@Profile("default")
	public TimeService DefaultTimeService(TimeRepository timeRepository) {
		return new DefaultTimeService(timeRepository);
	}
}
