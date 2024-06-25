package roomescape.domain;

import roomescape.dto.TimeDTO;

public record TimeEntity(Long id, String time) {

    public static TimeDTO toDTO(TimeEntity entity) {
        return new TimeDTO(entity.id(), entity.time());
    }
}
