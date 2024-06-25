package roomescape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.repository.JdbcReservationRepository;
import roomescape.repository.JdbcTimeRepository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig {

    @Bean
    public ReservationRepository reservationRepository(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return new JdbcReservationRepository(jdbcTemplate);
    }

    @Bean
    public TimeRepository timeRepository(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return new JdbcTimeRepository(jdbcTemplate);
    }
}