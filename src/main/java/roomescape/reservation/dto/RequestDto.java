package roomescape.reservation.dto;

import lombok.Data;
import roomescape.reservation.exception.exception.NoDataException;

import java.util.Arrays;

@Data
public class RequestDto {
    private String name;
    private String date;
    private Long time;

    public void validate() {
        validateBlanks(this.name,this.date);
    }

    public void validateBlanks(String... fields) {
        boolean isBlankExist = Arrays.stream(fields)
                .anyMatch(String::isBlank);
        if (isBlankExist) throw new NoDataException("빈 값이 입력되었습니다.");
    }
}