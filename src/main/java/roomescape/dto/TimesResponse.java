package roomescape.dto;

import java.util.List;

public record TimesResponse(
        List<TimeResponse> times
) {
    public static TimesResponse from(List<TimeResponse> times) {
        return new TimesResponse(times);
    }
}
