package roomescape.time.dto;

import roomescape.exception.BadRequestException;

import java.time.LocalTime;

public record TimeRequest(LocalTime time) {
    public TimeRequest {
        validateRequiredFields(time);
    }

    private void validateRequiredFields(LocalTime time) {
        if (time == null) {
            throw new BadRequestException("예약 요청에 시간이 누락되어 있습니다.");
        }
    }
}
