package roomescape;

import org.springframework.context.annotation.Bean;
import roomescape.controller.ReservationController;
import roomescape.repository.JdbcReservationRepository;

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
    public JdbcReservationRepository reservationRepository() {
        return new JdbcReservationRepository(dataSource);
    }
}
