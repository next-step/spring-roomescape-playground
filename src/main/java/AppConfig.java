import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import roomescape.web.dao.rowmapper.ReservationRowMapper;
import roomescape.web.dao.rowmapper.TimeRowMapper;

@Configuration
@ComponentScan(basePackages = {"roomescape.web.dao.rowmapper"})
public class AppConfig {

    @Bean
    public TimeRowMapper timeRowMapper() {
        return new TimeRowMapper();
    }

    @Bean
    public ReservationRowMapper reservationRowMapper() {
        return new ReservationRowMapper();
    }
}

