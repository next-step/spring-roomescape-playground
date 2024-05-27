package roomescape.dto.time;

import roomescape.domain.TimeDomain;
public record TimeResponse (Long id,
                            String time){
    public static TimeResponse from(TimeDomain time) {
        return new TimeResponse(
                time.getId(),
                time.getTime());
    }
}
