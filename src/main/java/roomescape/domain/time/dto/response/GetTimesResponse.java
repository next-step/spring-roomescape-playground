package roomescape.domain.time.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import roomescape.domain.time.entity.Time;

import java.time.LocalTime;

import static lombok.AccessLevel.*;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class GetTimesResponse {

    private final Long id;
    private final LocalTime time;

    public static GetTimesResponse from(Time time) {
        return new GetTimesResponse(time.getId(), time.getTime());
    }

}
