package roomescape.DTO;

import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.Model.JdbcTimeRepository;
import roomescape.Model.Reservation;
import roomescape.Model.Time;

import java.util.Arrays;

public record SaveReservationRequest(
        String name,
        String date,
        Long time
) {

    private static JdbcTimeRepository jdbcTimeRepository;

    public SaveReservationRequest {
        validate(name, date, time);
    }

    private void validate(String name, String date, Long time) {
        validateBlanks(name, date);
    }

    private void validateBlanks(String... fields) {
        boolean isBlank = Arrays.stream(fields).anyMatch(String::isBlank);
        if (isBlank) {
            throw new IllegalArgumentException("값이 입력되지 않았습니다");
        }
    }

//    public Reservation toReservation() {
//        return new Reservation(name, date, new Time(time));
//    }
}
