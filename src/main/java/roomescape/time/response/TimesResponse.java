package roomescape.time.response;

import java.util.List;
import roomescape.time.model.Time;

public record TimesResponse(List<TimeResponse> times) {

    public static TimesResponse of(List<Time> times) {
        return new TimesResponse(times.stream()
            .map(TimeResponse::new)
            .toList());
    }
}
