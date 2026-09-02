package roomescape.dto;

import roomescape.exception.BlankTimeException;

import java.time.DateTimeException;
import java.time.LocalTime;

public class TimeRequest {
    private String time;

    public TimeRequest() {
    }

    public LocalTime getParsedTime() {
        if (time == null || time.isBlank()) {
            throw new BlankTimeException("시간을 입력해주세요");
        }
        try {
            return LocalTime.parse(time);
        } catch (DateTimeException e) {
            throw new BlankTimeException("시간 형식이 올바르지 않습니다.");
        }
    }

    public String getTime() {
        return time;
    }
}
