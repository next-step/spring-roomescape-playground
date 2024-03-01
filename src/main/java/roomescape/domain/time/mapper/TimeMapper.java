package roomescape.domain.time.mapper;

import roomescape.domain.time.domain.Time;
import roomescape.domain.time.dto.TimeCreateDTO;
import roomescape.domain.time.dto.TimeResponseDTO;

public class TimeMapper {

    public static Time toEntity(TimeCreateDTO timeCreateDTO) {
        return new Time(null, timeCreateDTO.getTime());
    }

    public static TimeResponseDTO toTimeResponseDTO(Time time) {
        return new TimeResponseDTO(time.getId(), time.getTime());
    }
}
