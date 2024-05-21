package roomescape;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.controller.ReservationController;
import roomescape.repository.ReservationRepository;

import javax.sql.DataSource;

public class SpringConfig {

    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public ReservationController reservationController(){
        return new ReservationController(reservationRepository());
    }

    @Bean
    public ReservationRepository reservationRepository() {
        return new ReservationRepository(dataSource);
    }
}
