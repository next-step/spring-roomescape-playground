package com.cholog.roomescape.roomescape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import com.cholog.roomescape.roomescape.repository.ReservationRepository;
import com.cholog.roomescape.roomescape.repository.ReservationRepositoryImpl;
import com.cholog.roomescape.roomescape.service.ReservationService;
import com.cholog.roomescape.roomescape.service.ReservationServiceImpl;

@Configuration
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
