package roomescape.dto;

import roomescape.validation.ValidTime;

public record TimeCreateRequest(
        @ValidTime(message = "시간 형식이 올바르지 않습니다. 예: HH:mm")
        String time
) { }
