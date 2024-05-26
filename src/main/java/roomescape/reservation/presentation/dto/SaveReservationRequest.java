package roomescape.reservation.presentation.dto;

import java.util.Arrays;
import roomescape.reservation.presentation.exception.BadReservationSaveException;

public record SaveReservationRequest(
        String name,
        String date,
        Long time
) {

    public SaveReservationRequest {
        validate(name, date, time);
    }

    private void validate(String name, String date, Long time) {
        validateBlanks(name, date);
    }

    private void validateBlanks(String... fields) {
        boolean isBlankFieldExist = Arrays.stream(fields)
                                          .anyMatch(String::isBlank);
        if (isBlankFieldExist) {
            throw new BadReservationSaveException("빈 값이 입력되었습니다.");
        }
    }
}
