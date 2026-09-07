package roomescape;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

@TestConfiguration
public class TestClockConfig {

    @Bean
    @Primary
    Clock testClock() {
        ZoneId zone = ZoneId.of("Asia/Seoul");

        return Clock.fixed(
                LocalDateTime.of(2027, 8, 4, 12, 0)
                        .atZone(zone)
                        .toInstant(),
                zone
        );
    }
}
